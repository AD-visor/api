package com.advisor.api.iam.domain.auth.vo

data class OAuthCredentialProps(
    val provider: OAuthProvider,
    val oAuthId: String,
    val accessToken: String?
)
