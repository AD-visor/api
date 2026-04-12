package com.advisor.api.subscription.application.subscription

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.domain.vo.identifier.SubscriptionId
import com.advisor.api.common.core.infrastructure.DomainEventPublisher
import com.advisor.api.subscription.port.inbound.subscription.UseTokensUseCase
import com.advisor.api.subscription.port.inbound.subscription.command.UseTokensCommand
import com.advisor.api.subscription.port.outbound.plan.PlanStore
import com.advisor.api.subscription.port.outbound.subscription.SubscriptionStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UseTokensService(
    private val subscriptionStore: SubscriptionStore,
    private val planStore: PlanStore,
    private val domainEventPublisher: DomainEventPublisher
): UseTokensUseCase {
    @Transactional
    override fun execute(command: UseTokensCommand) {
        val subscription = subscriptionStore.loadByIdAndMemberId(
            id = SubscriptionId(command.id),
            memberId = MemberId(command.memberId)
        )
        val plan = planStore.loadById(subscription.planId)

        val updatedSubscription = subscription.useTokens(
            tokensToUse = command.tokensToUse,
            planLimit = plan.monthlyLimit.value
        )

        subscriptionStore.save(updatedSubscription)

        domainEventPublisher.publishFrom(updatedSubscription)
    }
}
