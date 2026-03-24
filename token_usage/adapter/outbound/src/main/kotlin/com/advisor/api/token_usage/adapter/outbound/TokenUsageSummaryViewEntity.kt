package com.advisor.api.token_usage.adapter.outbound

import com.advisor.api.token_usage.port.inbound.view.MonthlyTokenUsageView
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.Immutable
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneOffset

@Entity
@Table(name = "vw_token_usage_summary")
@Immutable
class TokenUsageSummaryViewEntity(
    @Id
    val subscriptionId: Long,
    val memberId: Long,
    val planId: Long,
    val billingMonth: Instant,
    val totalUsedTokens: Long,
    val requestCount: Long,
    val lastUsedAt: Instant?
) {
    fun toView() = MonthlyTokenUsageView(
        subscriptionId = subscriptionId,
        memberId = memberId,
        planId = planId,
        billingMonth = billingMonth.atZone(ZoneOffset.UTC)
            .let { YearMonth.of(it.year, it.month) },
        totalUsedTokens = totalUsedTokens,
        requestCount = requestCount,
        lastUsedAt = lastUsedAt
    )
}
