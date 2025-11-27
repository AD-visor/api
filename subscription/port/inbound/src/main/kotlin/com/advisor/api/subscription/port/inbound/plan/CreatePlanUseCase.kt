package com.advisor.api.subscription.port.inbound.plan

import com.advisor.api.subscription.port.inbound.plan.command.CreatePlanCommand

interface CreatePlanUseCase {
    fun execute(command: CreatePlanCommand)
}
