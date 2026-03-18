package com.advisor.api.subscription.port.inbound.plan

import com.advisor.api.subscription.port.inbound.plan.command.UpdatePlanCommand

interface UpdatePlanUseCase {
    fun execute(command: UpdatePlanCommand)
}
