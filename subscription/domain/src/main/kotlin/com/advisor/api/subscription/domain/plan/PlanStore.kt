package com.advisor.api.subscription.domain.plan

import com.advisor.api.common.core.domain.vo.identifier.PlanId

interface PlanStore {
    fun save(plan: Plan)
    fun loadById(id: PlanId): Plan
}
