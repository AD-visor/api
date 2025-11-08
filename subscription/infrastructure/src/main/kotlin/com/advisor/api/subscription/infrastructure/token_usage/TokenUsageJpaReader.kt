package com.advisor.api.subscription.infrastructure.token_usage

import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TokenUsageJpaReader: JpaRepository<TokenUsageViewEntity, Long> {
    @Query(
        """
            SELECT tu
            FROM TokenUsageViewEntity tu
            WHERE tu.subscriptionId = :subscriptionId
            AND tu.usedAt BETWEEN :startAt AND :endAt
            ORDER BY tu.usedAt DESC
        """
    )
    fun findTokenUsageViewsBySubscriptionId(
        @Param("subscriptionId") subscriptionId: Long,
        @Param("startAt") startAt: Long?,
        @Param("endAt") endAt: Long?,
    ): List<TokenUsageViewEntity>
}
