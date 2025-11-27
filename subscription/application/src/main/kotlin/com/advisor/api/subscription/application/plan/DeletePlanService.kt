package com.advisor.api.subscription.application.plan

import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.subscription.domain.plan.PlanStore
import com.advisor.api.subscription.port.inbound.plan.DeletePlanUseCase
import com.advisor.api.subscription.port.inbound.plan.command.DeletePlanCommand
import org.springframework.stereotype.Service

@Service
class DeletePlanService(
    val planStore: PlanStore
): DeletePlanUseCase {
    override fun execute(command: DeletePlanCommand) {
        val plan = planStore.loadById(PlanId(command.planId))
        val updatedPlan = plan.delete()

        planStore.save(updatedPlan)
    }
}
