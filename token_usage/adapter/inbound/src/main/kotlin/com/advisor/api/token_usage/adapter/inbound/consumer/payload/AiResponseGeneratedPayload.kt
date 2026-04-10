package com.advisor.api.token_usage.adapter.inbound.consumer.payload

import java.time.Instant

data class AiResponseGeneratedPayload(
    val memberId: Long,
    val conversationId: Long,
    val conversationMessageId: Long,
    val usedTokens: Long,
    val createdAt: Instant
)
