package com.advisor.api.subscription.port.inbound.token_usage.query

import java.time.Instant

data class GetTokenUsageListQuery(
    val memberId: Long,
    val startAt: Instant?,
    val endAt: Instant?
)
