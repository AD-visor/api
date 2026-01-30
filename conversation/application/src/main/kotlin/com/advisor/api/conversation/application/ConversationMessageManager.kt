package com.advisor.api.conversation.application

import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.infrastructure.SnowFlakeIdUtil
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.ConversationStore
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessage
import org.springframework.stereotype.Component

@Component
class ConversationMessageManager(
    private val conversationStore: ConversationStore,
    private val snowFlakeIdUtil: SnowFlakeIdUtil
) {
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

        return Pair(updatedConversation, message)
    }

    fun addAiMessage(
        conversation: Conversation,
        aiResponse: String,
        revisionOf: ConversationMessageId,
        parentMessageId: ConversationMessageId? = null
    ): Pair<Conversation, ConversationMessage> {
        val (updatedConversation, message) = conversation.addAiMessage(
            messageId = ConversationMessageId(snowFlakeIdUtil.generateId()),
            body = aiResponse,
            revisionOf = revisionOf,
            parentMessageId = parentMessageId
        )

        conversationStore.save(updatedConversation)
        conversationStore.saveNewMessage(message)

        return Pair(updatedConversation, message)
    }
}
