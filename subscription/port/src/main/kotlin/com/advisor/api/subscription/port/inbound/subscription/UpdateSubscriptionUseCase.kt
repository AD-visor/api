package com.advisor.api.subscription.port.inbound.subscription

import com.advisor.api.subscription.port.inbound.subscription.command.UpdateSubscriptionCommand

interface UpdateSubscriptionUseCase {
    fun execute(command: UpdateSubscriptionCommand)
}
