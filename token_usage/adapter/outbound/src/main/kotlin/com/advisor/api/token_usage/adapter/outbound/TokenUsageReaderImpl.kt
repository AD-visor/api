package com.advisor.api.token_usage.adapter.outbound

import com.advisor.api.token_usage.domain.TokenUsageReader
import com.advisor.api.token_usage.domain.TokenUsageView
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class TokenUsageReaderImpl(
    private val jpaReader: TokenUsageJpaReader
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
}
