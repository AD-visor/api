package com.advisor.api.conversation.port.inbound

import com.advisor.api.conversation.port.inbound.command.DeleteConversationCommand

interface DeleteConversationUseCase {
    fun execute(command: DeleteConversationCommand)
}
