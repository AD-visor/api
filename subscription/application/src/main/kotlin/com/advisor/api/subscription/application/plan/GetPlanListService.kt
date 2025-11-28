package com.advisor.api.subscription.application.plan

import com.advisor.api.subscription.domain.plan.PlanReader
import com.advisor.api.subscription.port.inbound.plan.GetPlanListUseCase
import com.advisor.api.subscription.port.inbound.plan.result.GetPlanResult
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
