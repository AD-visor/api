package com.advisor.api.subscription.port.inbound.subscription.command

data class RegisterSubscriptionCommand(
    val planId: Long,
    val memberId: Long,
    val paymentId: Long,
    val term: String
)
