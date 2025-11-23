package com.advisor.api.app.config.security.jwt

import com.advisor.api.app.config.security.SecurityExceptionCode
import com.advisor.api.app.config.security.SecurityPathFilter
import com.advisor.api.common.exception.CustomException
import com.advisor.api.iam.port.outbound.auth.AuthTokenPort
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val authTokenPort: AuthTokenPort
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val path = request.requestURI

        when {
            SecurityPathFilter.isPublicPath(path) -> {
                filterChain.doFilter(request, response)
                return
            }

            SecurityPathFilter.isRefreshPath(path) -> {
                val refreshToken = extractTokenByName(request, "refreshToken")
                authenticateFromRefreshToken(refreshToken, request)
            }

            SecurityPathFilter.isAuthPath(path) -> {
                filterChain.doFilter(request, response)
                return
            }

            else -> {
                val accessToken = extractTokenByName(request, "accessToken")
                authenticateFromAccessToken(accessToken, request)
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun extractTokenByName(request: HttpServletRequest, name: String): String {
        val cookies = request.cookies ?: throw CustomException(
            SecurityExceptionCode.MISSING_COOKIE,
            "쿠키가 존재하지 않습니다."
        )

        return cookies.find { it.name == name }?.value ?: throw CustomException(
            SecurityExceptionCode.MISSING_TOKEN,
            "${name}이 존재하지 않습니다."
        )
    }

    private fun authenticateFromAccessToken(
        accessToken: String,
        request: HttpServletRequest
    ) {
        authTokenPort.validateAccessToken(accessToken)
        if (SecurityContextHolder.getContext().authentication != null) return

        val subject = authTokenPort.getAccessSubject(accessToken)
        request.setAttribute("accessToken", accessToken)

        setAuthenticationContext(subject)
    }

    private fun authenticateFromRefreshToken(
        refreshToken: String,
        request: HttpServletRequest
    ) {
        authTokenPort.validateRefreshToken(refreshToken)
        if (SecurityContextHolder.getContext().authentication != null) return

        val subject = authTokenPort.getRefreshSubject(refreshToken)
        request.setAttribute("refreshToken", refreshToken)

        setAuthenticationContext(subject)
    }

    private fun setAuthenticationContext(subject: String) {
        val authentication = UsernamePasswordAuthenticationToken(
            subject,
            null,
            emptyList()
        )

        SecurityContextHolder.getContext().authentication = authentication
    }
}
