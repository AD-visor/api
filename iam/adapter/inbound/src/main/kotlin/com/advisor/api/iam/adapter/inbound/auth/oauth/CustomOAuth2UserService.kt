package com.advisor.api.iam.adapter.inbound.auth.oauth

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val oAuth2UserInfoFactory: OAuth2UserInfoFactory
): DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)

        val provider = userRequest.clientRegistration.registrationId
        val accessToken = userRequest.accessToken.tokenValue

        val userInfo = oAuth2UserInfoFactory.of(provider, oAuth2User.attributes)

        return CustomOAuth2User(
            authorities = setOf(SimpleGrantedAuthority("ROLE_USER")),
            attributes = oAuth2User.attributes,
            userInfo = userInfo
        )
    }
}
