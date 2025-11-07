package com.advisor.api.subscription.domain.plan

import com.advisor.api.common.core.domain.vo.Money
import com.advisor.api.subscription.domain.plan.vo.MonthlyLimit
import java.time.Instant

data class PlanProps(
    val name: String,
    val monthlyLimit: MonthlyLimit,
    val price: Money,
    val description: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?
)
