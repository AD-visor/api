package com.advisor.api.subscription.domain.subscription

import java.time.Instant

data class SubscriptionView(
    val id: Long,
    val subscriptionStatus: String,
    val monthlyUsage: Long,
    val memberId: Long,
    val planId: Long,
    val paymentId: Long,
    val startAt: Instant,
    val expiredAt: Instant
)
