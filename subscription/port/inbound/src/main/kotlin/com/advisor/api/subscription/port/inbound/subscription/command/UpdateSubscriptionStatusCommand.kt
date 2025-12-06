package com.advisor.api.subscription.port.inbound.subscription.command

data class UpdateSubscriptionStatusCommand(
    val subscriptionId: Long,
    val memberId: Long,
)
