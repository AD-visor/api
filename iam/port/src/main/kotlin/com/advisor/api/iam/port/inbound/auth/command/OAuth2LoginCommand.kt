package com.advisor.api.iam.port.inbound.auth.command

data class OAuth2LoginCommand(
    val providerName: String,
    val oAuthId: String,
    val email: String
)
