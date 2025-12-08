package com.advisor.api.token_usage.domain

import com.advisor.api.common.core.domain.vo.identifier.*
import java.time.Instant

data class TokenUsageProps(
    val memberId: MemberId,
    val planId: PlanId,
    val subscriptionId: SubscriptionId,
    val usedTokens: Long,
    val usedAt: Instant,
    val conversationMessageId: ConversationMessageId,
)
