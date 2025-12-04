package com.advisor.api.subscription.adapter.outbound.token_usage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface TokenUsageJpaReader: JpaRepository<TokenUsageViewEntity, Long> {
    @Query(
        """
            SELECT tu
            FROM TokenUsageViewEntity tu
            WHERE tu.subscriptionId = :subscriptionId
            AND tu.usedAt >= COALESCE(:startAt, tu.usedAt)
            AND tu.usedAt <= COALESCE(:endAt, tu.usedAt)
            ORDER BY tu.usedAt DESC
        """
    )
    fun findTokenUsageViewsBySubscriptionId(
        @Param("subscriptionId") subscriptionId: Long,
        @Param("startAt") startAt: Instant?,
        @Param("endAt") endAt: Instant?,
    ): List<TokenUsageViewEntity>

    @Query(
        """
            SELECT tu
            FROM TokenUsageViewEntity tu
            WHERE tu.memberId = :memberId
            AND tu.usedAt >= COALESCE(:startAt, tu.usedAt)
            AND tu.usedAt <= COALESCE(:endAt, tu.usedAt)
            ORDER BY tu.usedAt DESC
        """
    )
    fun findTokenUsageViewsByMemberId(
        @Param("memberId") memberId: Long,
        @Param("startAt") startAt: Instant?,
        @Param("endAt") endAt: Instant?,
    ): List<TokenUsageViewEntity>
}
