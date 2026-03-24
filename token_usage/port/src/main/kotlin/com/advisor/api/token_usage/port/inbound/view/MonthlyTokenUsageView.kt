package com.advisor.api.token_usage.port.inbound.view

import java.time.Instant
import java.time.YearMonth

data class MonthlyTokenUsageView(
    val subscriptionId: Long,
    val memberId: Long,
    val planId: Long,
    val billingMonth: YearMonth,
    val totalUsedTokens: Long,
    val requestCount: Long,
    val lastUsedAt: Instant?
)
