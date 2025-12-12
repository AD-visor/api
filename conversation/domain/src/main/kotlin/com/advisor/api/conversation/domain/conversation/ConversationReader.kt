package com.advisor.api.conversation.domain.conversation

interface ConversationReader {
    fun findAllMetadataByMemberId(memberId: Long): List<ConversationMetadataView>
    fun refreshView()
    fun refreshMessageView()
}
