package com.advisor.api.subscription.application.plan

import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.subscription.domain.plan.PlanStore
import com.advisor.api.subscription.port.inbound.plan.UndeletePlanUseCase
import com.advisor.api.subscription.port.inbound.plan.command.UndeletePlanCommand
import org.springframework.stereotype.Service

@Service
class UndeletePlanService(
    val planStore: PlanStore
): UndeletePlanUseCase {
    override fun execute(command: UndeletePlanCommand) {
        val plan = planStore.loadById(PlanId(command.planId))

        if (plan.isDeleted) {
            plan.undelete()
            planStore.save(plan)
        }
    }
}
