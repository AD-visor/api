package com.advisor.api.subscription.adapter.inbound.plan.dto.request

import com.advisor.api.subscription.port.inbound.plan.command.UpdatePlanCommand

data class UpdatePlanReqDto(
    val name: String?,
    val monthlyLimit: Long?,
    val price: Float?,
    val description: String?
) {
    fun toCommand(planId: Long) = UpdatePlanCommand(
        planId = planId,
        name = name,
        monthlyLimit = monthlyLimit,
        price = price,
        description = description
    )
}
