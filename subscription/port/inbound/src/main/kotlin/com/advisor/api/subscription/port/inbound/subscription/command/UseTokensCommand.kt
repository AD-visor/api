package com.advisor.api.subscription.port.inbound.subscription.command

data class UseTokensCommand(
    val id: Long,
    val memberId: Long,
    val tokensToUse: Long,
    val planLimit: Long
)
