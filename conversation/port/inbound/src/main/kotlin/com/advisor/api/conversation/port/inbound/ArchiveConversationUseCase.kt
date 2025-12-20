package com.advisor.api.conversation.port.inbound

import com.advisor.api.conversation.port.inbound.command.ArchiveConversationCommand

interface ArchiveConversationUseCase {
    fun execute(command: ArchiveConversationCommand)
}
