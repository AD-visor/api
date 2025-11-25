package com.advisor.api.app.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        logger.warn("인증 실패: ${authException.message} - Path: ${request.requestURI}")

        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"

        val errorResponse = mapOf(
            "status" to 401,
            "error" to "UNAUTHORIZED",
            "message" to (authException.message ?: "인증이 필요한 경로 입니다."),
            "path" to request.requestURI,
            "timestamp" to System.currentTimeMillis()
        )

        response.writer.write(ObjectMapper().writeValueAsString(errorResponse))
    }
}
