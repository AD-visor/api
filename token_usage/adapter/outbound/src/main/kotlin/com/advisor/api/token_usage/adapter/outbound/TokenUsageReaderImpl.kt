package com.advisor.api.token_usage.adapter.outbound

import com.advisor.api.token_usage.port.inbound.view.MonthlyTokenUsageView
import com.advisor.api.token_usage.port.outbound.TokenUsageReader
import com.advisor.api.token_usage.port.inbound.view.TokenUsageView
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneOffset

@Repository
class TokenUsageReaderImpl(
    private val jpaReader: TokenUsageJpaReader,
    private val summaryJpaReader: TokenUsageSummaryJpaReader,
    private val em: EntityManager
): TokenUsageReader {
    override fun findTokenUsageViewsBySubscriptionId(
        subscriptionId: Long,
        startAt: Instant?,
        endAt: Instant?
    ): List<TokenUsageView> {
        val entities = jpaReader.findTokenUsageViewsBySubscriptionId(
            subscriptionId,
            startAt,
            endAt
        )

        return entities.map { it.toModel() }
    }

    override fun findTokenUsageViewsByMemberId(
        memberId: Long,
        startAt: Instant?,
        endAt: Instant?
    ): List<TokenUsageView> {
        val entities = jpaReader.findTokenUsageViewsByMemberId(memberId, startAt, endAt)

        return entities.map { it.toModel() }
    }

    override fun findMonthlySummary(
        subscriptionId: Long?,
        memberId: Long?,
        month: YearMonth
    ): List<MonthlyTokenUsageView> {
        val billingMonth = month.atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant()

        return when {
            subscriptionId != null -> summaryJpaReader
                .findBySubscriptionIdAndBillingMonth(subscriptionId, billingMonth)
            memberId != null -> summaryJpaReader
                .findByMemberIdAndBillingMonth(memberId, billingMonth)
            else -> emptyList()
        }.map { it.toView() }
    }

    override fun refreshView() {
        em.createNativeQuery("REFRESH MATERIALIZED VIEW CONCURRENTLY vw_token_usage")
            .executeUpdate()
    }
}
