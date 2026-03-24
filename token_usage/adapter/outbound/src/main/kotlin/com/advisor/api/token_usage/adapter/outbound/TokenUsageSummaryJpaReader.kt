package com.advisor.api.token_usage.adapter.outbound

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.Instant

interface TokenUsageSummaryJpaReader : JpaRepository<TokenUsageSummaryViewEntity, Long> {
    @Query("""
        SELECT t FROM TokenUsageSummaryViewEntity t
        WHERE t.subscriptionId = :subscriptionId
          AND t.billingMonth = :billingMonth
    """)
    fun findBySubscriptionIdAndBillingMonth(
        subscriptionId: Long,
        billingMonth: Instant
    ): List<TokenUsageSummaryViewEntity>

    @Query("""
        SELECT t FROM TokenUsageSummaryViewEntity t
        WHERE t.subscriptionId = :subscriptionId
          AND t.memberId = :memberId
          AND t.billingMonth = :billingMonth
    """)
    fun findBySubscriptionIdAndMemberIdAndBillingMonth(
        subscriptionId: Long,
        memberId: Long,
        billingMonth: Instant
    ): List<TokenUsageSummaryViewEntity>

    @Query("""
        SELECT t FROM TokenUsageSummaryViewEntity t
        WHERE t.memberId = :memberId
          AND t.billingMonth = :billingMonth
        ORDER BY t.billingMonth DESC
    """)
    fun findByMemberIdAndBillingMonth(
        memberId: Long,
        billingMonth: Instant
    ): List<TokenUsageSummaryViewEntity>
}
