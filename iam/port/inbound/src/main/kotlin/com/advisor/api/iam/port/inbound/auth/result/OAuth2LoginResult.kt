package com.advisor.api.iam.port.inbound.auth.result

data class OAuth2LoginResult(
    val accessToken: String,
    val refreshToken: String
)
