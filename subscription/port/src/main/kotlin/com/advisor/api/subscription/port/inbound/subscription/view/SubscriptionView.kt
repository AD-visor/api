package com.advisor.api.subscription.port.inbound.subscription.view

import java.time.Instant

data class SubscriptionView(
    val id: Long,
    val subscriptionStatus: String,
    val monthlyUsage: Long,
    val memberId: Long,
    val planId: Long,
    val paymentId: Long,
    val startedAt: Instant,
    val expiredAt: Instant
)