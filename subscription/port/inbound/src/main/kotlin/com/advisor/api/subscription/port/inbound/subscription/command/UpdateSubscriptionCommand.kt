package com.advisor.api.subscription.port.inbound.subscription.command

import java.time.Instant

data class UpdateSubscriptionCommand(
    val id: Long,
    val subscriptionStatus: String?,
    val monthlyUsage: Long?,
    val planId: Long?,
    val memberId: Long?,
    val paymentId: Long?,
    val startedAt: Instant?,
    val expiredAt: Instant?
)
