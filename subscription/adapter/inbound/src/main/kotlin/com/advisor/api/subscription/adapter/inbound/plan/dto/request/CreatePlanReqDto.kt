package com.advisor.api.subscription.adapter.inbound.plan.dto.request

import com.advisor.api.subscription.port.inbound.plan.command.CreatePlanCommand

data class CreatePlanReqDto(
    val name: String,
    val monthlyLimit: Long,
    val price: Float,
    val description: String?
) {
    fun toCommand() = CreatePlanCommand(
        name = name,
        monthlyLimit = monthlyLimit,
        price = price,
        description = description
    )
}
