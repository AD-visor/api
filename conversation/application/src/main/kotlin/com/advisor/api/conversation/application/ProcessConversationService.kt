package com.advisor.api.conversation.application

import com.advisor.api.common.core.domain.DomainEventPublisher
import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.ConversationReader
import com.advisor.api.conversation.domain.conversation.ConversationStore
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import com.advisor.api.conversation.port.inbound.ProcessConversationUseCase
import com.advisor.api.conversation.port.inbound.command.ProcessConversationCommand
import com.advisor.api.media.port.inbound.CreateMediaUseCase
import com.advisor.api.media.port.inbound.command.CreateMediaCommand
import com.advisor.api.media.port.outbound.ImageEditPort
import com.advisor.api.media.port.outbound.command.ImageEditCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProcessConversationService(
    private val conversationStore: ConversationStore,
    private val conversationReader: ConversationReader,
    private val conversationMessageManager: ConversationMessageManager,
    private val aiCreativeOrchestrator: AiCreativeOrchestrator,
    private val createMediaUseCase: CreateMediaUseCase,
    private val imageEditPort: ImageEditPort,
    private val domainEventPublisher: DomainEventPublisher
) : ProcessConversationUseCase {

    @Transactional
    override fun execute(command: ProcessConversationCommand) {
        // 대화 및 메시지 조회
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

        /*
        saga 패턴을 활용하여 단계별로 끊기.
        트랜잭션이 너무 길어져 DB를 너무 오래 점유해 성능 저하 가능성.
        중간 단계에서 실패 시, 이전 작업을 취소 시켜야 함.
        */

        // 2. AI 결과물 생성(광고 문구 - 배경 이미지 - 텍스트 디자인)
        val aiCreativeResult = aiCreativeOrchestrator.orchestrate(
            conversation = updatedConversation,
            messages = messages,
            userRequest = command.body
        )


        // 3. 이미지 결합
        val compositeCommand = ImageEditCommand.Composite(
            baseImage = aiCreativeResult.imageBytes,
            textElements = aiCreativeResult.textElements
        )
        val finalImageBytes = imageEditPort.composite(compositeCommand).image

        // 4. 미디어 저장
        val createMediaCommand = CreateMediaCommand(
            conversationId = command.conversationId,
            conversationMessageId = memberMessage.id.value,
            memberId = command.memberId,
            file = finalImageBytes,
            mimeType = "image/png",
            width = compositeCommand.canvasWidth,
            height = compositeCommand.canvasHeight
        )
        createMediaUseCase.execute(listOf(createMediaCommand))

        // 5. AI 메시지 저장
        val (finalConversation, aiMessage) = conversationMessageManager.addAiMessage(
            conversation = updatedConversation,
            aiResponse = "Copywriting: ${aiCreativeResult.copyWrite}\nImage has been generated and saved.",
            revisionOf = memberMessage.id,
            parentMessageId = ConversationMessageId(command.aiMessageId)
        )

        domainEventPublisher.publish(finalConversation)
    }

    private fun loadConversationContext(
        conversationId: Long,
        aiMessageId: Long,
        memberId: Long
    ): Pair<Conversation, List<ConversationMessageView>> {
        val conversation = conversationStore.loadByIdAndMemberId(
            id = ConversationId(conversationId),
            memberId = MemberId(memberId)
        )

        val messages = conversationReader.findPairByAiMessageIdAndConversationIdAndMemberId(
            aiMessageId = aiMessageId,
            conversationId = conversationId,
            memberId = memberId
        )

        return Pair(conversation, messages)
    }
}
