package com.advisor.api.iam.adapter.inbound.auth.oauth

import com.advisor.api.common.core.presentation.CookieManager
import com.advisor.api.iam.port.inbound.auth.OAuth2LoginUseCase
import com.advisor.api.iam.port.inbound.auth.command.OAuth2LoginCommand
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    private val oAuth2LoginUseCase: OAuth2LoginUseCase,
    private val cookieManager: CookieManager
): AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val oAuth2User = authentication.principal as CustomOAuth2User
        val oAuthUserInfo = oAuth2User.getUserInfo()

        val command = OAuth2LoginCommand(
            providerName = oAuthUserInfo.providerName,
            oAuthId = oAuthUserInfo.oAuthId,
            email = oAuthUserInfo.email
        )

        val result = oAuth2LoginUseCase.execute(command)

        cookieManager.setAccessTokenCookie(response, result.accessToken)
        cookieManager.setRefreshTokenCookie(response, result.refreshToken)
    }
}
