package com.advisor.api.subscription.adapter.inbound.plan.dto.response

import com.advisor.api.subscription.port.inbound.plan.result.GetPlanResult
import java.time.Instant

data class PlanResDto(
    val id: Long,
    val name: String,
    val monthlyLimit: Long,
    val price: Float,
    val description: String?,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    companion object {
        fun fromResult(result: GetPlanResult): PlanResDto {
            return PlanResDto(
                id = result.id,
                name = result.name,
                monthlyLimit = result.monthlyLimit,
                price = result.price,
                description = result.description,
                createdAt = result.createdAt,
                updatedAt = result.updatedAt
            )
        }
    }
}
