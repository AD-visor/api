package com.advisor.api.conversation.application

import com.advisor.api.common.core.domain.DomainEventPublisher
import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.infrastructure.SnowFlakeIdUtil
import com.advisor.api.conversation.domain.conversation.ConversationStore
import com.advisor.api.conversation.domain.conversation.vo.MessageRole
import com.advisor.api.conversation.port.inbound.AddMemberMessageUseCase
import com.advisor.api.conversation.port.inbound.command.AddMemberMessageCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddMemberMessageService(
    private val conversationStore: ConversationStore,
    private val snowFlakeIdUtil: SnowFlakeIdUtil,
    private val domainEventPublisher: DomainEventPublisher
): AddMemberMessageUseCase {
    @Transactional
    override fun execute(command: AddMemberMessageCommand) {
        val conversation = conversationStore.loadById(ConversationId(command.conversationId))
        val (updatedConversation, message)= conversation.addMemberMessage(
            messageId = ConversationMessageId(snowFlakeIdUtil.generateId()),
            body = command.body
        )

        conversationStore.save(updatedConversation)
        conversationStore.saveNewMessage(message)

        domainEventPublisher.publish(updatedConversation)
    }
}
