package com.advisor.api.subscription.port.inbound.plan

import com.advisor.api.subscription.port.inbound.plan.result.GetPlanResult

interface GetPlanListUseCase {
    fun execute(): List<GetPlanResult>
}
