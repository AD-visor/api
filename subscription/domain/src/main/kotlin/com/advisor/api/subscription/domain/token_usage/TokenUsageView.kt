package com.advisor.api.subscription.domain.token_usage

import java.time.Instant

data class TokenUsageView(
    val id: Long,
    val memberId: Long,
    val subscriptionId: Long,
    val usedTokens: Long,
    val usedAt: Instant,
    val conversationMessageId: Long
)
