package com.advisor.api.subscription.port.inbound.subscription

import com.advisor.api.subscription.port.inbound.subscription.command.UpdateSubscriptionStatusCommand

interface SuspendSubscriptionUseCase {
    fun execute(command: UpdateSubscriptionStatusCommand)
}
