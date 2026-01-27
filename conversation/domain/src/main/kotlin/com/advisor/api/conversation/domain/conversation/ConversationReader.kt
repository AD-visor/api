package com.advisor.api.conversation.domain.conversation

import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView

interface ConversationReader {
    fun findByIdAndMemberId(
        id: Long,
        memberId: Long,
        limit: Int? = null
    ): ConversationView
    fun findAllMetadataByMemberId(memberId: Long): List<ConversationMetadataView>
    fun findPairByAiMessageIdAndMemberId(
        aiMessageId: Long,
        memberId: Long
    ): List<ConversationMessageView>
    fun refreshView()
    fun refreshMessageView()
}
