package com.advisor.api.conversation.domain.conversation.entity

import java.time.Instant

data class ConversationMessageView(
    val id: Long,
    val role: String,
    val body: String,
    val revisionOf: String?,
    val createdAt: Instant
)
