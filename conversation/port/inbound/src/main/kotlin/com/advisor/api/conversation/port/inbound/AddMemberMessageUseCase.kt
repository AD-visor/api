package com.advisor.api.conversation.port.inbound

import com.advisor.api.conversation.port.inbound.command.AddMemberMessageCommand

interface AddMemberMessageUseCase {
    fun execute(command: AddMemberMessageCommand)
}
