package com.advisor.api.app.config.security.jwt

import com.advisor.api.app.config.security.SecurityPathFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val path = request.requestURI

        if (SecurityPathFilter.isPublicPath(path) || SecurityPathFilter.isAuthPath(path)) {
            filterChain.doFilter(request, response)
            return
        }

        filterChain.doFilter(request, response)
    }
}
