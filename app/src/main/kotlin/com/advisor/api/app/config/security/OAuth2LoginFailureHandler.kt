package com.advisor.api.app.config.security

import com.advisor.api.app.config.security.properties.FrontendProperties
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.net.URLEncoder

@Component
class OAuth2LoginFailureHandler(
    private val frontendProperties: FrontendProperties
): AuthenticationFailureHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        logger.error("OAuth2 로그인 실패: ${exception.message}", exception)

        val errorMessage = URLEncoder.encode(exception.message ?: "OAuth2 로그인 실패", "UTF-8")
        response.sendRedirect(frontendProperties.url + "/login?error=$errorMessage")
    }
}
