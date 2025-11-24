package com.advisor.api.iam.port.inbound.auth.command

data class RenewTokenCommand(
    val refreshToken: String,
    val memberId: Long
)
