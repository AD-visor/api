package com.advisor.api.iam.adapter.inbound.auth.oauth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOAuth2User(
    private val userInfo: OAuth2UserInfo,
    private val authorities: Collection<GrantedAuthority>,
    private val attributes: Map<String, Any>
) : OAuth2User {

    override fun getAttributes() = attributes
    override fun getAuthorities() = authorities

    override fun getName(): String = userInfo.oAuthId

    fun getUserInfo() = userInfo
}
