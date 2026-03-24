package com.advisor.api.token_usage.port.outbound

import com.advisor.api.token_usage.port.inbound.view.MonthlyTokenUsageView
import com.advisor.api.token_usage.port.inbound.view.TokenUsageView
import java.time.Instant
import java.time.YearMonth

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

    fun findMonthlySummary(
        subscriptionId: Long?,
        memberId: Long?,
        month: YearMonth
    ): List<MonthlyTokenUsageView>

    fun refreshView()
}
