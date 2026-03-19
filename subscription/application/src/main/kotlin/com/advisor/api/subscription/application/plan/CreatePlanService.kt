package com.advisor.api.subscription.application.plan

import com.advisor.api.common.core.domain.DomainEventPublisher
import com.advisor.api.common.core.domain.vo.Money
import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.common.core.infrastructure.SnowFlakeIdUtil
import com.advisor.api.subscription.domain.plan.Plan
import com.advisor.api.subscription.domain.plan.PlanProps
import com.advisor.api.subscription.domain.plan.vo.MonthlyLimit
import com.advisor.api.subscription.port.inbound.plan.CreatePlanUseCase
import com.advisor.api.subscription.port.inbound.plan.command.CreatePlanCommand
import com.advisor.api.subscription.port.outbound.plan.PlanStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class CreatePlanService(
    private val planStore: PlanStore,
    private val snowFlakeIdUtil: SnowFlakeIdUtil,
    private val domainEventPublisher: DomainEventPublisher
): CreatePlanUseCase {
    @Transactional
    override fun execute(command: CreatePlanCommand) {
        val planProps = PlanProps(
            name = command.name,
            monthlyLimit = MonthlyLimit.create(command.monthlyLimit),
            price = Money.create(command.price),
            description = command.description,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            isDeleted = false,
            deletedAt = null
        )

        val plan = Plan.create(PlanId(snowFlakeIdUtil.generateId()), planProps)

        planStore.save(plan)

        domainEventPublisher.publish(plan)
    }
}
