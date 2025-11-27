package com.advisor.api.subscription.port.inbound.plan

import com.advisor.api.subscription.port.inbound.plan.command.UndeletePlanCommand

interface UndeletePlanUseCase {
    fun execute(command: UndeletePlanCommand)
}
