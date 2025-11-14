package com.advisor.api.conversation.domain.conversation

import java.time.Instant

data class ConversationView(
    val id: Long,
    val memberId: Long,
    val businessType: String,
    val productName: String,
    val description: String,
    val targetAudience: String,
    val toneStyle: String,
    val speechStyle: String,
    val contentLength: String,
    val platform: String,
    val createdAt: Instant
)
