package com.advisor.api.conversation.port.inbound.view

import java.time.Instant

data class ConversationMetadataView(
    val id: Long,
    val productName: String,
    val updatedAt: Instant
)