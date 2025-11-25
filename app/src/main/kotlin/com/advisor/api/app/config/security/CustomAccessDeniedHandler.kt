package com.advisor.api.app.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class CustomAccessDeniedHandler : AccessDeniedHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        logger.warn("접근 권한 없음: ${accessDeniedException.message} - Path: ${request.requestURI}")

        response.status = HttpServletResponse.SC_FORBIDDEN
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"

        val errorResponse = mapOf(
            "status" to 403,
            "error" to "FORBIDDEN",
            "message" to "Access denied. Insufficient permissions.",
            "path" to request.requestURI,
            "timestamp" to System.currentTimeMillis()
        )

        response.writer.write(ObjectMapper().writeValueAsString(errorResponse))
    }
}
