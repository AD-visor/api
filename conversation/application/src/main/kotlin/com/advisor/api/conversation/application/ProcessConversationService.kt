package com.advisor.api.conversation.application

import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.port.outbound.ConversationReader
import com.advisor.api.conversation.port.outbound.ConversationStore
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessage
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import com.advisor.api.conversation.domain.conversation.vo.MessageStatus
import com.advisor.api.conversation.port.inbound.ProcessConversationUseCase
import com.advisor.api.conversation.port.inbound.command.ProcessConversationCommand
import com.advisor.api.media.port.inbound.CreateMediaUseCase
import com.advisor.api.media.port.inbound.command.CreateMediaCommand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import kotlin.coroutines.cancellation.CancellationException

@Service
class ProcessConversationService(
    private val conversationStore: ConversationStore,
    private val conversationReader: ConversationReader,
    private val conversationMessageManager: ConversationMessageManager,
    private val aiCreativeOrchestrator: AiCreativeOrchestrator,
    private val createMediaUseCase: CreateMediaUseCase
) : ProcessConversationUseCase {

    private val logger = KotlinLogging.logger {}

    override suspend fun execute(command: ProcessConversationCommand) {
        logger.info { "========== ProcessConversation 시작 ==========" }
        logger.info { "conversationId: ${command.conversationId}" }
        logger.info { "memberId: ${command.memberId}" }
        logger.info { "userRequest: ${command.body}" }

        try {
            // 1. 컨텍스트 로드
            logger.info { "[1/4] 컨텍스트 로드" }
            val (conversation, messages) = loadConversationContext(
                conversationId = command.conversationId,
                aiMessageId = command.aiMessageId,
                memberId = command.memberId
            )

            // 2. 회원 메시지 저장
            logger.info { "[2/4] 회원 메시지 저장" }
            val (updatedConversation, memberMessage) = withContext(Dispatchers.IO) {
                conversationMessageManager.addMemberMessage(
                    conversation = conversation,
                    body = command.body
                )
            }
            logger.info { "회원 메시지 저장 완료: messageId=${memberMessage.id}" }

            // 3. AI 메시지 초기화
            logger.info { "[3/4] AI 메시지 초기화" }
            val aiMessage = withContext(Dispatchers.IO) {
                conversationMessageManager.createInitialAiMessage(
                    conversation = updatedConversation,
                    revisionOf = memberMessage.id,
                    parentMessageId = command.aiMessageId?.let { ConversationMessageId(it) }
                )
            }
            logger.info { "AI 메시지 초기화 완료: aiMessageId=${aiMessage.id}" }

            // 4. AI 파이프라인 실행
            logger.info { "[4/4] AI 파이프라인 실행" }
            processAiPipeline(
                conversation = updatedConversation,
                messages = messages,
                aiMessage = aiMessage,
                command = command
            )

            logger.info { "========== ProcessConversation 완료 ==========" }

        } catch (e: Exception) {
            logger.error(e) {
                """
                ========== ProcessConversation 실패 ==========
                conversationId: ${command.conversationId}
                원인: ${e.cause?.message ?: e.message}
                """.trimIndent()
            }
            throw e
        }
    }

    private suspend fun loadConversationContext(
        conversationId: Long,
        aiMessageId: Long?,
        memberId: Long
    ): Pair<Conversation, List<ConversationMessageView>> {
        return withContext(Dispatchers.IO) {
            try {
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

                Pair(conversation, messages)

            } catch (e: Exception) {
                logger.error(e) { "컨텍스트 로드 실패: conversationId=$conversationId" }
                throw e
            }
        }
    }

    private suspend fun processAiPipeline(
        conversation: Conversation,
        messages: List<ConversationMessageView>,
        aiMessage: ConversationMessage,
        command: ProcessConversationCommand
    ) {
        var currentMessage = aiMessage

        try {
            /* STEP 1: 통합 디자인 생성 */
            logger.info { ">>> 통합 디자인 생성 시작" }

            currentMessage = withContext(Dispatchers.IO) {
                conversationMessageManager.updateMessageStatus(
                    conversation = conversation,
                    message = currentMessage,
                    nextStatus = MessageStatus.PROCESSING
                )
            }

            val creativeResult = aiCreativeOrchestrator.orchestrate(
                conversation = conversation,
                message = currentMessage,
                messages = messages,
                userRequest = command.body
            )

            currentMessage = creativeResult.conversationMessage
            logger.info { ">>> 통합 디자인 생성 완료: ${creativeResult.imageBytes.size} bytes" }

            /* STEP 2: 미디어 저장 */
            logger.info { ">>> 미디어 저장 시작" }
            withContext(Dispatchers.IO) {
                createMediaUseCase.execute(
                    listOf(
                        CreateMediaCommand(
                            conversationId = command.conversationId,
                            conversationMessageId = currentMessage.id.value,
                            memberId = command.memberId,
                            file = creativeResult.imageBytes,
                            mimeType = "image/png",
                            width = 1024,
                            height = 1024
                        )
                    )
                )
            }
            logger.info { ">>> 미디어 저장 완료" }

            /* STEP 3: 최종 완료 */
            logger.info { ">>> 최종 완료 처리" }
            withContext(Dispatchers.IO) {
                conversationMessageManager.updateMessageStatus(
                    conversation = conversation,
                    message = currentMessage,
                    nextStatus = MessageStatus.COMPLETED
                )

                conversationMessageManager.completeAiMessage(
                    conversation = conversation,
                    messageId = currentMessage.id,
                    aiResponse = buildAiResponse(),
                    revisionOf = currentMessage.revisionOf ?: throw CustomException(
                        code = ConversationApplicationExceptionCode.CONVERSATION_MESSAGE_REVISION_OF_NOT_FOUND,
                        data = "[Conversation] AI 메시지의 revisionOf가 존재하지 않습니다."
                    ),
                    parentMessageId = currentMessage.parentMessageId
                )
            }
            logger.info { ">>> 최종 완료" }

        } catch (e: CancellationException) {
            logger.warn { "AI 파이프라인 취소됨" }
            throw e

        } catch (e: Exception) {
            logger.error(e) {
                """
                AI 파이프라인 실패
                conversationId: ${command.conversationId}
                currentStatus: ${currentMessage.status}
                에러: ${e::class.simpleName}
                메시지: ${e.message}
                """.trimIndent()
            }

            withContext(Dispatchers.IO) {
                conversationMessageManager.updateMessageStatus(
                    conversation = conversation,
                    message = currentMessage,
                    nextStatus = MessageStatus.FAILED
                )
            }

            handleFailure(conversation, currentMessage, e)
        }
    }

    private suspend fun handleFailure(
        conversation: Conversation,
        aiMessage: ConversationMessage,
        e: Exception
    ) {
        logger.error { "실패 처리 시작: messageId=${aiMessage.id}" }

        withContext(Dispatchers.IO) {
            conversationMessageManager.completeAiMessage(
                conversation = conversation,
                messageId = aiMessage.id,
                aiResponse = buildFailureResponse(e),
                revisionOf = aiMessage.revisionOf ?: throw CustomException(
                    code = ConversationApplicationExceptionCode.CONVERSATION_MESSAGE_REVISION_OF_NOT_FOUND,
                    data = "[Conversation] AI 메시지의 revisionOf가 존재하지 않습니다."
                ),
                parentMessageId = aiMessage.parentMessageId
            )
        }

        throw e
    }

    private fun buildAiResponse(): String {
        return """
        🎨 AI 디자인 완성
        
        프로페셔널한 Instagram 광고 이미지가 생성되었습니다.
        한글 텍스트가 포함된 완성된 디자인입니다.
        """.trimIndent()
    }

    private fun buildFailureResponse(e: Exception): String {
        return """
        ❌ AI 처리 실패
        
        에러 타입: ${e::class.simpleName}
        에러 메시지: ${e.message}
        ${if (e.cause != null) "원인: ${e.cause?.message}" else ""}
        
        잠시 후 다시 시도해주세요.
        """.trimIndent()
    }
}
