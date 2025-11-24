package com.advisor.api.common.core.presentation

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component

@Component
class CookieManager {
    fun setCookie(
        response: HttpServletResponse,
        name: String,
        value: String,
        httpOnly: Boolean = true,
        secure: Boolean = true,
        maxAge: Int,
        path: String = "/"
    ) {
        val cookie = ResponseCookie.from(name, value)
            .httpOnly(httpOnly)
            .secure(secure)
            .path(path)
            .maxAge(maxAge.toLong())
            .sameSite("None")
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
    }

    fun setAccessTokenCookie(response: HttpServletResponse, token: String) {
        setCookie(
            response = response,
            name = "accessToken",
            value = token,
            maxAge = 86400
        )
    }

    fun setRefreshTokenCookie(response: HttpServletResponse, token: String) {
        setCookie(
            response = response,
            name = "refreshToken",
            value = token,
            maxAge = 2592000
        )
    }

    fun deleteCookie(response: HttpServletResponse, name: String) {
        setCookie(
            response = response,
            name = name,
            value = "",
            maxAge = 0
        )
    }
}