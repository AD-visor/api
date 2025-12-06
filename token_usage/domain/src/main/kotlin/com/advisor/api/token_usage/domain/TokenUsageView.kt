package com.advisor.api.token_usage.domain

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
