package com.advisor.api.conversation.application

import com.advisor.api.common.core.domain.DomainEventPublisher
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.infrastructure.SnowFlakeIdUtil
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.port.outbound.ConversationStore
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessage
import com.advisor.api.conversation.domain.conversation.vo.MessageStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ConversationMessageManager(
    private val conversationStore: ConversationStore,
    private val snowFlakeIdUtil: SnowFlakeIdUtil,
    private val domainEventPublisher: DomainEventPublisher
) {
    @Transactional
    fun addMemberMessage(
        conversation: Conversation,
        body: String
    ): Pair<Conversation, ConversationMessage> {
        val (updatedConversation, message) = conversation.addMemberMessage(
            messageId = ConversationMessageId(snowFlakeIdUtil.generateId()),
            body = body
        )

        conversationStore.save(updatedConversation)
        conversationStore.saveNewMessage(message)

        domainEventPublisher.publish(updatedConversation)

        return Pair(updatedConversation, message)
    }

    @Transactional
    fun createInitialAiMessage(
        conversation: Conversation,
        revisionOf: ConversationMessageId,
        parentMessageId: ConversationMessageId?
    ): ConversationMessage {
        val (updatedConversation, aiMessage) = conversation.initiateAiMessage(
            messageId = ConversationMessageId(snowFlakeIdUtil.generateId()),
            revisionOf = revisionOf,
            parentMessageId = parentMessageId
        )

        conversationStore.save(updatedConversation)
        conversationStore.saveNewMessage(aiMessage)

        domainEventPublisher.publish(updatedConversation)

        return aiMessage
    }

    @Transactional
    fun updateMessageStatus(
        conversation: Conversation,
        message: ConversationMessage,
        nextStatus: MessageStatus
    ): ConversationMessage {
        val (updatedConversation, updatedMessage) = conversation.updateMessageStatus(
            message = message,
            nextStatus = nextStatus
        )

        conversationStore.save(updatedConversation)
        conversationStore.saveNewMessage(updatedMessage)

        domainEventPublisher.publish(updatedConversation)

        return updatedMessage
    }

    @Transactional
    fun completeAiMessage(
        conversation: Conversation,
        messageId: ConversationMessageId,
        aiResponse: String,
        revisionOf: ConversationMessageId,
        parentMessageId: ConversationMessageId?
    ): Pair<Conversation, ConversationMessage> {
        val (updatedConversation, message) = conversation.completeAiMessage(
            messageId = messageId,
            body = aiResponse,
            revisionOf = revisionOf,
            parentMessageId = parentMessageId
        )

        conversationStore.save(updatedConversation)
        conversationStore.saveNewMessage(message)

        domainEventPublisher.publish(updatedConversation)

        return Pair(updatedConversation, message)
    }
}
