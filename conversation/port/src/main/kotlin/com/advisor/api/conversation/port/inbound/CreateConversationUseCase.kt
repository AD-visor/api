package com.advisor.api.conversation.port.inbound

import com.advisor.api.conversation.port.inbound.command.CreateConversationCommand
import com.advisor.api.conversation.port.inbound.result.CreateConversationResult

interface CreateConversationUseCase {
    fun execute(command: CreateConversationCommand): CreateConversationResult
}
