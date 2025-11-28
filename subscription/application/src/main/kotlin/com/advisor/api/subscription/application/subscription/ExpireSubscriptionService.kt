package com.advisor.api.subscription.application.subscription

import com.advisor.api.common.core.domain.vo.identifier.SubscriptionId
import com.advisor.api.subscription.domain.subscription.SubscriptionStore
import com.advisor.api.subscription.port.inbound.subscription.command.UpdateSubscriptionStatusCommand
import com.advisor.api.subscription.port.inbound.subscription.ExpireSubscriptionUseCase
import org.springframework.stereotype.Service

@Service
class ExpireSubscriptionService(
    private val subscriptionStore: SubscriptionStore
): ExpireSubscriptionUseCase {
    override fun execute(command: UpdateSubscriptionStatusCommand) {
        val subscription = subscriptionStore.loadById(SubscriptionId(command.subscriptionId))
        val updatedSubscription = subscription.expire()

        subscriptionStore.save(updatedSubscription)
    }
}
