package com.advisor.api.conversation.port.inbound.command

data class ArchiveConversationCommand(
    val id: Long,
    val memberId: Long
)
