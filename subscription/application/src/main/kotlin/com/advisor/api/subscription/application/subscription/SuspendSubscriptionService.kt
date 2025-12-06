package com.advisor.api.subscription.application.subscription

import com.advisor.api.common.core.domain.DomainEventPublisher
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.domain.vo.identifier.SubscriptionId
import com.advisor.api.subscription.domain.subscription.SubscriptionStore
import com.advisor.api.subscription.port.inbound.subscription.SuspendSubscriptionUseCase
import com.advisor.api.subscription.port.inbound.subscription.command.UpdateSubscriptionStatusCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuspendSubscriptionService(
    private val subscriptionStore: SubscriptionStore,
    private val domainEventPublisher: DomainEventPublisher,
): SuspendSubscriptionUseCase {
    @Transactional
    override fun execute(command: UpdateSubscriptionStatusCommand) {
        val subscription = subscriptionStore.loadByIdAndMemberId(
            id = SubscriptionId(command.subscriptionId),
            memberId = MemberId(command.memberId),
        )
        val updatedSubscription = subscription.suspend()

        subscriptionStore.save(updatedSubscription)

        domainEventPublisher.publish(updatedSubscription)
    }
}
