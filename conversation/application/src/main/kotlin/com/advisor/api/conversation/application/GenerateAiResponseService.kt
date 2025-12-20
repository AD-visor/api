package com.advisor.api.conversation.application

import com.advisor.api.common.core.domain.DomainEventPublisher
import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.infrastructure.SnowFlakeIdUtil
import com.advisor.api.conversation.domain.conversation.ConversationStore
import com.advisor.api.conversation.port.inbound.GenerateAiResponseUseCase
import com.advisor.api.conversation.port.inbound.command.GenerateAiResponseCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class GenerateAiResponseService(
    private val conversationStore: ConversationStore,
    private val snowFlakeIdUtil: SnowFlakeIdUtil,
    private val domainEventPublisher: DomainEventPublisher
): GenerateAiResponseUseCase {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun execute(command: GenerateAiResponseCommand) {
        val conversation = conversationStore.loadByIdAndMemberId(
            id = ConversationId(command.conversationId),
            memberId = MemberId(command.memberId)
        )

        // 실제 ai 생성 로직 구현 후 대체 예정
        val aiResponse = "test response 대답이요"

        val (updatedConversation, message) = conversation.addAiMessage(
            messageId = ConversationMessageId(snowFlakeIdUtil.generateId()),
            body = aiResponse,
            revisionOf = ConversationMessageId(command.revisionOf)
        )

        conversationStore.save(updatedConversation)
        conversationStore.saveNewMessage(message)

        domainEventPublisher.publish(updatedConversation)
    }
}
