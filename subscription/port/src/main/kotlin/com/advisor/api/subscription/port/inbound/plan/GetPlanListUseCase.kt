package com.advisor.api.subscription.port.inbound.plan

import com.advisor.api.subscription.port.inbound.plan.view.PlanView

interface GetPlanListUseCase {
    fun execute(): List<PlanView>
}
