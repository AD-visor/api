package com.advisor.api.subscription.port.inbound.plan.result

import com.advisor.api.subscription.domain.plan.PlanView
import java.time.Instant

data class GetPlanResult(
    val id: Long,
    val name: String,
    val monthlyLimit: Long,
    val price: Float,
    val description: String?,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    companion object {
        fun fromModel(model: PlanView): GetPlanResult {
            return GetPlanResult(
                id = model.id,
                name = model.name,
                monthlyLimit = model.monthlyLimit,
                price = model.price,
                description = model.description,
                createdAt = model.createdAt,
                updatedAt = model.updatedAt
            )
        }
    }
}
