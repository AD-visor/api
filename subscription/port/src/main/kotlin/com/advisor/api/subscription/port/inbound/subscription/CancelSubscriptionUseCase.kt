package com.advisor.api.subscription.port.inbound.subscription

import com.advisor.api.subscription.port.inbound.subscription.command.UpdateSubscriptionStatusCommand

interface CancelSubscriptionUseCase {
    fun execute(command: UpdateSubscriptionStatusCommand)
}
