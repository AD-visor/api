package com.advisor.api.subscription.infrastructure.token_usage

import com.advisor.api.subscription.domain.token_usage.TokenUsageReader
import com.advisor.api.subscription.domain.token_usage.TokenUsageView
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
}
