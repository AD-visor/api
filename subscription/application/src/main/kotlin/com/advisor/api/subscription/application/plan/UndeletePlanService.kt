package com.advisor.api.subscription.application.plan

import com.advisor.api.common.core.domain.DomainEventPublisher
import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.subscription.port.inbound.plan.UndeletePlanUseCase
import com.advisor.api.subscription.port.inbound.plan.command.UndeletePlanCommand
import com.advisor.api.subscription.port.outbound.plan.PlanStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UndeletePlanService(
    private val planStore: PlanStore,
    private val domainEventPublisher: DomainEventPublisher
): UndeletePlanUseCase {
    @Transactional
    override fun execute(command: UndeletePlanCommand) {
        val plan = planStore.loadById(PlanId(command.planId))

        if (plan.isDeleted) {
            val updatedPlan = plan.undelete()
            planStore.save(updatedPlan)
        }

        domainEventPublisher.publish(plan)
    }
}
