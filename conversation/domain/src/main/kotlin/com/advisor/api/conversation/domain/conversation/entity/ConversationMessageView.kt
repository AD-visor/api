package com.advisor.api.conversation.domain.conversation.entity

import java.time.Instant

data class ConversationMessageView(
    val id: Long,
    val conversationId: Long,
    val memberId: Long,
    val role: String,
    val body: String,
    val revisionOf: Long?,
    val parentMessageId: Long?,
    val createdAt: Instant
)
