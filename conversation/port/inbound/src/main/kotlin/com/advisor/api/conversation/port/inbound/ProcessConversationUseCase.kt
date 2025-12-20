package com.advisor.api.conversation.port.inbound

import com.advisor.api.conversation.port.inbound.command.ProcessConversationCommand

interface ProcessConversationUseCase {
    fun execute(command: ProcessConversationCommand)
}
