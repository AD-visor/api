package com.advisor.api.token_usage.port.inbound.query

import java.time.Instant

data class GetTokenUsageListQuery(
    val memberId: Long,
    val startAt: Instant?,
    val endAt: Instant?
)
