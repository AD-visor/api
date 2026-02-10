package com.advisor.api.conversation.application

import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.ConversationReader
import com.advisor.api.conversation.domain.conversation.ConversationStore
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessage
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import com.advisor.api.conversation.domain.conversation.vo.MessageStatus.Companion.COMPLETED
import com.advisor.api.conversation.domain.conversation.vo.MessageStatus.Companion.COMPOSITING
import com.advisor.api.conversation.domain.conversation.vo.MessageStatus.Companion.FAILED
import com.advisor.api.conversation.port.inbound.ProcessConversationUseCase
import com.advisor.api.conversation.port.inbound.command.ProcessConversationCommand
import com.advisor.api.media.port.inbound.CreateMediaUseCase
import com.advisor.api.media.port.inbound.command.CreateMediaCommand
import com.advisor.api.media.port.outbound.ImageEditPort
import com.advisor.api.media.port.outbound.command.ImageEditCommand
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import kotlin.coroutines.cancellation.CancellationException

@Service
class ProcessConversationService(
    private val conversationStore: ConversationStore,
    private val conversationReader: ConversationReader,
    private val conversationMessageManager: ConversationMessageManager,
    private val aiCreativeOrchestrator: AiCreativeOrchestrator,
    private val createMediaUseCase: CreateMediaUseCase,
    private val imageEditPort: ImageEditPort
) : ProcessConversationUseCase {

    private val serviceScope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO + CoroutineName("AiProcessingScope")
    )

    override fun execute(command: ProcessConversationCommand) {
        val (conversation, messages) = loadConversationContext(
            conversationId = command.conversationId,
            aiMessageId = command.aiMessageId,
            memberId = command.memberId
        )

        // 1. 회원 메시지 저장
        val (updatedConversation, memberMessage) = conversationMessageManager.addMemberMessage(
            conversation = conversation,
            body = command.body
        )

        val aiMessage = conversationMessageManager.createInitialAiMessage(
            conversation = updatedConversation,
            revisionOf = memberMessage.id,
            parentMessageId = command.aiMessageId?.let { ConversationMessageId(it) }
        )

        serviceScope.launch {
            processAiPipeline(
                conversation = updatedConversation,
                messages = messages,
                aiMessage = aiMessage,
                command = command
            )
        }
    }

    private fun loadConversationContext(
        conversationId: Long,
        aiMessageId: Long?,
        memberId: Long
    ): Pair<Conversation, List<ConversationMessageView>> {
        val conversation = conversationStore.loadByIdAndMemberId(
            id = ConversationId(conversationId),
            memberId = MemberId(memberId)
        )

        val messages = if (aiMessageId == null) {
            emptyList()
        } else {
            conversationReader.findPairByAiMessageIdAndConversationIdAndMemberId(
                aiMessageId = aiMessageId,
                conversationId = conversationId,
                memberId = memberId
            )
        }

        return Pair(conversation, messages)
    }

    private suspend fun processAiPipeline(
        conversation: Conversation,
        messages: List<ConversationMessageView>,
        aiMessage: ConversationMessage,
        command: ProcessConversationCommand
    ) {
        var currentConversation = conversation
        var currentMessage = aiMessage

        try {
            /* -----------------------------
             * STEP 1: Copywriting → Image → Layout(AiCreativeOrchestrator에서 진행)
             * ----------------------------- */
            val creativeResult = aiCreativeOrchestrator.orchestrate(
                conversation = conversation,
                message = currentMessage,
                messages = messages,
                userRequest = command.body
            )

            currentMessage = creativeResult.conversationMessage

            /* -----------------------------
             * STEP 2: Image compositing
             * ----------------------------- */
            currentMessage = conversationMessageManager.updateMessageStatus(
                conversation = currentConversation,
                message = currentMessage,
                nextStatus = COMPOSITING
            )

            val compositeCommand = ImageEditCommand.Composite(
                baseImage = creativeResult.imageBytes,
                textElements = creativeResult.textElements
            )

            val compositeResult = imageEditPort.composite(compositeCommand)

            /* -----------------------------
             * STEP 3: Media persistence
             * ----------------------------- */
            createMediaUseCase.execute(
                listOf(
                    CreateMediaCommand(
                        conversationId = command.conversationId,
                        conversationMessageId = currentMessage.id.value,
                        memberId = command.memberId,
                        file = compositeResult.image,
                        mimeType = "image/png",
                        width = compositeCommand.canvasWidth,
                        height = compositeCommand.canvasHeight
                    )
                )
            )

            /* -----------------------------
             * STEP 4: Final AI message
             * ----------------------------- */
            currentMessage = conversationMessageManager.updateMessageStatus(
                conversation = currentConversation,
                message = currentMessage,
                nextStatus = COMPLETED
            )

            conversationMessageManager.completeAiMessage(
                conversation = conversation,
                messageId = currentMessage.id,
                aiResponse = buildAiResponse(creativeResult),
                revisionOf = currentMessage.revisionOf ?: throw CustomException(
                    code = ConversationApplicationExceptionCode.CONVERSATION_MESSAGE_REVISION_OF_NOT_FOUND,
                    data = "[Conversation] AI 메시지의 revisionOf가 존재하지 않습니다."
                ),
                parentMessageId = currentMessage.parentMessageId
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            currentMessage = conversationMessageManager.updateMessageStatus(
                conversation = currentConversation,
                message = currentMessage,
                nextStatus = FAILED
            )
            handleFailure(
                conversation = conversation,
                aiMessageId = currentMessage.id,
                revisionOf = currentMessage.revisionOf ?: throw CustomException(
                    code = ConversationApplicationExceptionCode.CONVERSATION_MESSAGE_REVISION_OF_NOT_FOUND,
                    data = "[Conversation] AI 메시지의 revisionOf가 존재하지 않습니다."
                ),
                parentMessageId = currentMessage.parentMessageId,
                e = e
            )
        }
    }

    private suspend fun handleFailure(
        conversation: Conversation,
        aiMessageId: ConversationMessageId,
        revisionOf: ConversationMessageId,
        parentMessageId: ConversationMessageId?,
        e: Exception
    ) {
        conversationMessageManager.completeAiMessage(
            conversation = conversation,
            messageId = aiMessageId,
            aiResponse = "AI processing failed.\nReason: ${e.message}",
            revisionOf = revisionOf,
            parentMessageId = parentMessageId
        )

        throw RuntimeException("AI processing failed", e)
    }

    private fun buildAiResponse(
        result: AiCreativeOrchestrator.AiCreativeResult
    ): String {
        return """
        🎨 AI Creative Completed
        
        ✍️ Copywriting:
        ${result.copyWrite}
        
        🖼 Image has been generated and saved.
    """.trimIndent()
    }

}
