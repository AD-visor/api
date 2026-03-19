package com.advisor.api.iam.port.inbound.auth.command

data class OAuth2LogoutCommand(
    val memberId: Long
)
