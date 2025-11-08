package com.advisor.api.subscription.infrastructure.token_usage

import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.Instant

interface TokenUsageJpaReader: JpaRepository<TokenUsageViewEntity, Long> {
    @Query(
        """
            SELECT tu
            FROM TokenUsageViewEntity tu
            WHERE tu.subscriptionId = :subscriptionId
            AND (:startAt IS NULL OR tu.usedAt >= :startAt) AND (:endAt IS NULL OR tu.usedAt <= :endAt)
            ORDER BY tu.usedAt DESC
        """
    )
    fun findTokenUsageViewsBySubscriptionId(
        @Param("subscriptionId") subscriptionId: Long,
        @Param("startAt") startAt: Instant?,
        @Param("endAt") endAt: Instant?,
    ): List<TokenUsageViewEntity>
}
