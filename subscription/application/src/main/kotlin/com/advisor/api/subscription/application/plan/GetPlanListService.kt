package com.advisor.api.subscription.application.plan

import com.advisor.api.subscription.port.inbound.plan.GetPlanListUseCase
import com.advisor.api.subscription.port.inbound.plan.view.PlanView
import com.advisor.api.subscription.port.outbound.plan.PlanReader
import org.springframework.stereotype.Service

@Service
class GetPlanListService(
    private val planReader: PlanReader
): GetPlanListUseCase {
    override fun execute(): List<PlanView> {
        return planReader.findAll()
    }
}
