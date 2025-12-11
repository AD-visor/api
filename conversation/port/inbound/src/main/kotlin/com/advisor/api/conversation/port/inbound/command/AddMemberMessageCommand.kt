package com.advisor.api.conversation.port.inbound.command

data class AddMemberMessageCommand(
    val conversationId: Long,
    val memberId: Long,
    val body: String
)
