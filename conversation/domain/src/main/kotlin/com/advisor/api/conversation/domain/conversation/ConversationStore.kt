package com.advisor.api.conversation.domain.conversation

import com.advisor.api.common.core.domain.vo.identifier.ConversationId

interface ConversationStore {
    fun save(conversation: Conversation)
    fun loadById(id: ConversationId): Conversation
}
