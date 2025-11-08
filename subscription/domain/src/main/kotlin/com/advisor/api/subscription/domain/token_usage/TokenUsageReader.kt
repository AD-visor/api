package com.advisor.api.subscription.domain.token_usage

import java.time.Instant

interface TokenUsageReader {
    fun findTokenUsageViewsBySubscriptionId(
        subscriptionId: Long,
        startAt: Instant?,
        endAt: Instant?,
    ): List<TokenUsageView>
}
