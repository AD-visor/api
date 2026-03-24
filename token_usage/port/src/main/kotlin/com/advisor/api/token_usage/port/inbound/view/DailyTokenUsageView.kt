package com.advisor.api.token_usage.port.inbound.view

import java.time.Instant

data class DailyTokenUsageView(
    val usageDate: Instant,
    val dailyUsedTokens: Long,
    val dailyRequestCount: Long
)
