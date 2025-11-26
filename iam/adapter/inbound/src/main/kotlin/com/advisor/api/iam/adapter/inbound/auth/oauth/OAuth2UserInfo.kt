package com.advisor.api.iam.adapter.inbound.auth.oauth

interface OAuth2UserInfo {
    val providerName: String
    val oAuthId: String
    val email: String
}
