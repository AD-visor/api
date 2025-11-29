package com.advisor.api.subscription.port.inbound.subscription

import com.advisor.api.subscription.port.inbound.subscription.command.UpdateSubscriptionStatusCommand

interface ResumeSubscriptionUseCase {
    fun execute(command: UpdateSubscriptionStatusCommand)
}
