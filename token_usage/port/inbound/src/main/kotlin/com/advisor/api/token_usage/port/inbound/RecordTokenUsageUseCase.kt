package com.advisor.api.token_usage.port.inbound

import com.advisor.api.token_usage.port.inbound.command.RecordTokenUsageCommand

interface RecordTokenUsageUseCase {
    fun execute(command: RecordTokenUsageCommand)
}
