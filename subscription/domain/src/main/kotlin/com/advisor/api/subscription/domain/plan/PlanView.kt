package com.advisor.api.subscription.domain.plan

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
