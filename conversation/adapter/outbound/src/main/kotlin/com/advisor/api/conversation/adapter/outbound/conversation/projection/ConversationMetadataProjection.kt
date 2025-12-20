package com.advisor.api.conversation.adapter.outbound.conversation.projection

import java.time.Instant

interface ConversationMetadataProjection {
    fun getId(): Long
    fun getProductName(): String
    fun getUpdatedAt(): Instant
}
