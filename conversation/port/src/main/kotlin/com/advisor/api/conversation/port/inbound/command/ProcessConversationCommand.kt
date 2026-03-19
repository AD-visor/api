package com.advisor.api.conversation.port.inbound.command

data class ProcessConversationCommand(
    val conversationId: Long,
    val aiMessageId: Long?,
    val memberId: Long,
    val body: String
)
