package com.advisor.api.token_usage.port.inbound.query

import java.time.YearMonth

data class GetMonthlyTokenUsageQuery(
    val subscriptionId: Long,
    val memberId: Long,
    val month: YearMonth
)
