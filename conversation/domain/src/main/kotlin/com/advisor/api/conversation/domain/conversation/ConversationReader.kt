package com.advisor.api.conversation.domain.conversation

interface ConversationReader {
    fun findByIdAndMemberId(id: Long, memberId: Long): ConversationView
    fun findAllMetadataByMemberId(memberId: Long): List<ConversationMetadataView>
    fun refreshView()
    fun refreshMessageView()
}
