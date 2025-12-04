package com.advisor.api.subscription.port.inbound.token_usage.result

import com.advisor.api.subscription.domain.token_usage.TokenUsageView
import java.time.Instant

data class GetTokenUsageResult(
    val id: Long,
    val memberId: Long,
    val subscriptionId: Long,
    val usedTokens: Long,
    val usedAt: Instant,
    val conversationMessageId: Long
) {
    companion object {
        fun fromModel(model: TokenUsageView): GetTokenUsageResult {
            return GetTokenUsageResult(
                id = model.id,
                memberId = model.memberId,
                subscriptionId = model.subscriptionId,
                usedTokens = model.usedTokens,
                usedAt = model.usedAt,
                conversationMessageId = model.conversationMessageId
            )
        }
    }
}
