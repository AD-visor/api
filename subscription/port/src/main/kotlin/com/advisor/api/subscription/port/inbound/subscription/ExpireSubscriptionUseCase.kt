package com.advisor.api.subscription.port.inbound.subscription

import com.advisor.api.subscription.port.inbound.subscription.command.UpdateSubscriptionStatusCommand

interface ExpireSubscriptionUseCase {
    fun execute(command: UpdateSubscriptionStatusCommand)
}
