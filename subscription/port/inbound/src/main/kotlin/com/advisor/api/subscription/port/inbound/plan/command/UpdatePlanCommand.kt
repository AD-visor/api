package com.advisor.api.subscription.port.inbound.plan.command

data class UpdatePlanCommand(
    val planId: Long,
    val name: String?,
    val monthlyLimit: Long?,
    val price: Float?,
    val description: String?,
)
