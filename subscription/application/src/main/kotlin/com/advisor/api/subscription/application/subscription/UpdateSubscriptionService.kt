package com.advisor.api.subscription.application.subscription

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.domain.vo.identifier.PaymentId
import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.common.core.domain.vo.identifier.SubscriptionId
import com.advisor.api.common.core.infrastructure.DomainEventPublisher
import com.advisor.api.subscription.domain.subscription.vo.MonthlyUsage
import com.advisor.api.subscription.domain.subscription.vo.SubscriptionStatus
import com.advisor.api.subscription.port.inbound.subscription.UpdateSubscriptionUseCase
import com.advisor.api.subscription.port.inbound.subscription.command.UpdateSubscriptionCommand
import com.advisor.api.subscription.port.outbound.subscription.SubscriptionStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateSubscriptionService(
    private val subscriptionStore: SubscriptionStore,
    private val domainEventPublisher: DomainEventPublisher
) : UpdateSubscriptionUseCase {
    @Transactional
    override fun execute(command: UpdateSubscriptionCommand) {
        val subscription = subscriptionStore.loadById(SubscriptionId(command.id))

        val updatedSubscription = subscription.update(
            newSubscriptionStatus = command.subscriptionStatus?.let { SubscriptionStatus.create(it) }
                ?: subscription.subscriptionStatus,
            newMonthlyUsage = command.monthlyUsage?.let { MonthlyUsage.create(it) }
                ?: subscription.monthlyUsage,
            newPlanId = command.planId?.let { PlanId(it) } ?: subscription.planId,
            newMemberId = command.memberId?.let { MemberId(it) } ?: subscription.memberId,
            newPaymentId = command.paymentId?.let { PaymentId(it) } ?: subscription.paymentId,
            newStartedAt = command.startedAt ?: subscription.startedAt,
            newExpiredAt = command.expiredAt ?: subscription.expiredAt
        )

        subscriptionStore.save(updatedSubscription)

        domainEventPublisher.publishFrom(updatedSubscription)
    }
}
