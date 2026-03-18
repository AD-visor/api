package com.advisor.api.token_usage.port.inbound.view

import java.time.Instant

data class TokenUsageView(
    val id: Long,
    val memberId: Long,
    val planId: Long,
    val subscriptionId: Long,
    val usedTokens: Long,
    val usedAt: Instant,
    val conversationMessageId: Long
)
