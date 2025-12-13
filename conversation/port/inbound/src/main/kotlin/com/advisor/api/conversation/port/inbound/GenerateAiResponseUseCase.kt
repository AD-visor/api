package com.advisor.api.conversation.port.inbound

import com.advisor.api.conversation.port.inbound.command.GenerateAiResponseCommand

interface GenerateAiResponseUseCase {
    fun execute(command: GenerateAiResponseCommand)
}
