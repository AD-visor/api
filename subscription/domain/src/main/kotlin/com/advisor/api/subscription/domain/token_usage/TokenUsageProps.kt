package com.advisor.api.subscription.domain.token_usage

import com.advisor.api.common.core.domain.vo.identifier.*
import com.advisor.api.subscription.domain.token_usage.vo.SourceContext
import java.time.Instant

data class TokenUsageProps(
    val memberId: MemberId,
    val planId: PlanId,
    val subscriptionId: SubscriptionId,
    val usedTokens: Long,
    val usedAt: Instant,
    val conversationMessageId: ConversationMessageId,
)
