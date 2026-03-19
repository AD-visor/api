package com.advisor.api.conversation.port.inbound.command

data class GenerateAiResponseCommand(
    val conversationId: Long,
    val memberId: Long,
    val revisionOf: Long
)
