package com.advisor.api.iam.port.inbound.auth.result

data class RenewTokenResult(
    val accessToken: String,
    val refreshToken: String
)
