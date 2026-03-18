package com.advisor.api.subscription.port.inbound.subscription.result

import com.advisor.api.subscription.port.inbound.subscription.view.SubscriptionView
import java.time.Instant

data class GetSubscriptionResult(
    val id: Long,
    val subscriptionStatus: String,
    val monthlyUsage: Long,
    val planId: Long,
    val memberId: Long,
    val startedAt: Instant,
    val expiredAt: Instant
) {
    companion object {
        fun fromModel(model: SubscriptionView): GetSubscriptionResult {
            return GetSubscriptionResult(
                id = model.id,
                subscriptionStatus = model.subscriptionStatus,
                monthlyUsage = model.monthlyUsage,
                planId = model.planId,
                memberId = model.memberId,
                startedAt = model.startedAt,
                expiredAt = model.expiredAt
            )
        }
    }
}
