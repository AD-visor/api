package com.advisor.api.subscription.port.outbound.plan

import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.subscription.domain.plan.Plan

interface PlanStore {
    fun save(plan: Plan)
    fun loadById(id: PlanId): Plan
}
