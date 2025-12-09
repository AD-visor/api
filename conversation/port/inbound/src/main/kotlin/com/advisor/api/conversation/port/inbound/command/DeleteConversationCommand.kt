package com.advisor.api.conversation.port.inbound.command

data class DeleteConversationCommand(
    val id: Long,
    val memberId: Long
)
