package com.advisor.api.subscription.domain.token_usage

data class TokenUsageView(
    val id: Long,
    val memberId: Long,
    val subscriptionId: Long,
    val usedTokens: Long,
    val usedAt: Long,
    val sourceContext: String,
    val contentRequestId: Long?,
    val contentRevisionId: Long?,
    val contentId: Long?
)
