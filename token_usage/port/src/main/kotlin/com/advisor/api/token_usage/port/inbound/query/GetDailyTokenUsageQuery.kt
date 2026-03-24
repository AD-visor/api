package com.advisor.api.token_usage.port.inbound.query

import java.time.Instant
import java.time.YearMonth
import java.time.ZoneOffset

data class GetDailyTokenUsageQuery(
    val subscriptionId: Long,
    val memberId: Long,
    val from: Instant = YearMonth.now()
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant(),
    val to: Instant = YearMonth.now()
        .atEndOfMonth()
        .atTime(23, 59, 59)
        .toInstant(ZoneOffset.UTC)
)
