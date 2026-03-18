package com.advisor.api.subscription.port.inbound.plan.view

import java.time.Instant

data class PlanView(
    val id: Long,
    val name: String,
    val monthlyLimit: Long,
    val price: Float,
    val description: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
)