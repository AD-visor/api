package com.advisor.api.subscription.port.inbound.subscription

import com.advisor.api.subscription.port.inbound.subscription.command.RegisterSubscriptionCommand

interface RegisterSubscriptionUseCase {
    fun execute(command: RegisterSubscriptionCommand)
}
