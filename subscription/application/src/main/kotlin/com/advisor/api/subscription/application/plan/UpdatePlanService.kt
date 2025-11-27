package com.advisor.api.subscription.application.plan

import com.advisor.api.common.core.domain.vo.Money
import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.subscription.domain.plan.PlanStore
import com.advisor.api.subscription.domain.plan.vo.MonthlyLimit
import com.advisor.api.subscription.port.inbound.plan.UpdatePlanUseCase
import com.advisor.api.subscription.port.inbound.plan.command.UpdatePlanCommand
import org.springframework.stereotype.Service

@Service
class UpdatePlanService(
    private val planStore: PlanStore
): UpdatePlanUseCase {
    override fun execute(command: UpdatePlanCommand) {
        val plan = planStore.loadById(PlanId(command.planId))

        val updatedPlan = plan.update(
            newName = command.name ?: plan.name,
            newMonthlyLimit = command.monthlyLimit?.let { MonthlyLimit.create(it) } ?: plan.monthlyLimit,
            newPrice = command.price?.let { Money.create(it) } ?: plan.price,
            newDescription = command.description
        )

        planStore.save(updatedPlan)
    }
}
