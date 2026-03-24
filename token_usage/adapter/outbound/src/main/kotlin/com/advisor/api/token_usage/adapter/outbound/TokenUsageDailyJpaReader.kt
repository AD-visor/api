package com.advisor.api.token_usage.adapter.outbound

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.Instant

interface TokenUsageDailyJpaReader : JpaRepository<TokenUsageDailyEntity, Long> {
    @Query("""
        SELECT t FROM TokenUsageDailyEntity t
        WHERE t.subscriptionId = :subscriptionId
          AND t.memberId = :memberId
          AND t.usageDate BETWEEN :from AND :to
        ORDER BY t.usageDate ASC
    """)
    fun findBySubscriptionIdAndMemberIdAndUsageDateBetween(
        subscriptionId: Long,
        memberId: Long,
        from: Instant,
        to: Instant
    ): List<TokenUsageDailyEntity>
}
