package com.advisor.api.conversation.port.inbound.view

import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import java.time.Instant

data class ConversationView(
    val id: Long,
    val businessType: String,
    val productName: String,
    val description: String,
    val targetAudience: String,
    val toneStyle: String,
    val platform: String,
    val messages: List<ConversationMessageView>,
    val createdAt: Instant,
    val updatedAt: Instant
)