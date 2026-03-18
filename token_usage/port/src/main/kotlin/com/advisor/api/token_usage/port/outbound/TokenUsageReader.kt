package com.advisor.api.token_usage.port.outbound

import java.time.Instant

interface TokenUsageReader {
    fun findTokenUsageViewsBySubscriptionId(
        subscriptionId: Long,
        startAt: Instant?,
        endAt: Instant?,
    ): List<TokenUsageView>

    fun findTokenUsageViewsByMemberId(
        memberId: Long,
        startAt: Instant?,
        endAt: Instant?,
    ): List<TokenUsageView>

    fun refreshView()
}
