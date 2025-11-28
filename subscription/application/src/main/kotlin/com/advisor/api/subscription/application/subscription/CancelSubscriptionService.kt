package com.advisor.api.subscription.application.subscription

import com.advisor.api.common.core.domain.vo.identifier.SubscriptionId
import com.advisor.api.subscription.domain.subscription.SubscriptionStore
import com.advisor.api.subscription.port.inbound.subscription.CancelSubscriptionUseCase
import com.advisor.api.subscription.port.inbound.subscription.command.UpdateSubscriptionStatusCommand
import org.springframework.stereotype.Service

@Service
class CancelSubscriptionService(
    private val subscriptionStore: SubscriptionStore
): CancelSubscriptionUseCase {
    override fun execute(command: UpdateSubscriptionStatusCommand) {
        val subscription = subscriptionStore.loadById(SubscriptionId(command.subscriptionId))
        val updatedSubscription = subscription.cancel()

        subscriptionStore.save(updatedSubscription)
    }
}
