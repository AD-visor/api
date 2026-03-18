package com.advisor.api.subscription.application.plan

import com.advisor.api.subscription.port.inbound.plan.GetPlanListUseCase
import com.advisor.api.subscription.port.inbound.plan.result.GetPlanResult
import com.advisor.api.subscription.port.outbound.plan.PlanReader
import org.springframework.stereotype.Service

@Service
class GetPlanListService(
    private val planReader: PlanReader
): GetPlanListUseCase {
    override fun execute(): List<GetPlanResult> {
        val plans = planReader.findAll()

        return plans.map { GetPlanResult.fromModel(it) }
    }
}
