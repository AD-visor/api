package com.advisor.api.subscription.application.plan

import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.common.core.infrastructure.DomainEventPublisher
import com.advisor.api.subscription.port.inbound.plan.DeletePlanUseCase
import com.advisor.api.subscription.port.inbound.plan.command.DeletePlanCommand
import com.advisor.api.subscription.port.outbound.plan.PlanStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeletePlanService(
    private val planStore: PlanStore,
    private val domainEventPublisher: DomainEventPublisher
): DeletePlanUseCase {
    @Transactional
    override fun execute(command: DeletePlanCommand) {
        val plan = planStore.loadById(PlanId(command.planId))
        val updatedPlan = plan.delete()

        planStore.save(updatedPlan)

        domainEventPublisher.publishFrom(updatedPlan)
    }
}
