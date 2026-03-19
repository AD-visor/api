package com.advisor.api.subscription.port.inbound.subscription

import com.advisor.api.subscription.port.inbound.subscription.command.UseTokensCommand

interface UseTokensUseCase {
    fun execute(command: UseTokensCommand)
}
