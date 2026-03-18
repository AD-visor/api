package com.advisor.api.conversation.port.inbound

import com.advisor.api.conversation.port.inbound.command.UpdateConversationCommand

interface UpdateConversationUseCase {
    fun execute(command: UpdateConversationCommand)
}
