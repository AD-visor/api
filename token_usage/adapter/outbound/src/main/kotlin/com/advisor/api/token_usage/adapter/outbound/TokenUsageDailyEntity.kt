package com.advisor.api.token_usage.adapter.outbound

import com.advisor.api.token_usage.port.inbound.view.DailyTokenUsageView
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.Immutable
import java.time.Instant

@Entity
@Table(name = "vw_token_usage_daily")
@Immutable
class TokenUsageDailyEntity(

    @Id
    val subscriptionId: Long,

    val memberId: Long,
    val planId: Long,
    val usageDate: Instant,
    val dailyUsedTokens: Long,
    val dailyRequestCount: Long
) {
    fun toView() = DailyTokenUsageView(
        usageDate = usageDate,
        dailyUsedTokens = dailyUsedTokens,
        dailyRequestCount = dailyRequestCount
    )
}
