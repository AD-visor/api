package com.advisor.api.subscription.port.inbound.plan

import com.advisor.api.subscription.port.inbound.plan.command.DeletePlanCommand

interface DeletePlanUseCase {
    fun execute(command: DeletePlanCommand)
}
