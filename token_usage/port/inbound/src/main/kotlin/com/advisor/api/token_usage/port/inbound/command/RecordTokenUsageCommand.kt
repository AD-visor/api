package com.advisor.api.token_usage.port.inbound.command

data class RecordTokenUsageCommand(
    val memberId: Long,
    val subscriptionId: Long,
    val usedTokens: Long,
    val conversationMessageId: Long
)
