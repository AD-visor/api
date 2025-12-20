package com.advisor.api.conversation.domain.conversation

import java.time.Instant

data class ConversationMetadataView(
    val id: Long,
    val productName: String,
    val updatedAt: Instant
)
