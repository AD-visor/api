package com.advisor.api.conversation.domain.conversation

import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessage

interface ConversationStore {
    fun save(conversation: Conversation)
    fun saveNewMessage(message: ConversationMessage)
    fun loadById(id: ConversationId): Conversation
    fun loadByIdAndMemberId(
        id: ConversationId,
        memberId: MemberId,
        archived: Boolean? = null
    ): Conversation
    fun delete(id: ConversationId)
}
