package com.advisor.api.iam.adapter.inbound.auth

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.common.core.presentation.CookieManager
import com.advisor.api.common.core.presentation.CustomUserDetails
import com.advisor.api.iam.port.inbound.auth.RenewTokenUseCase
import com.advisor.api.iam.port.inbound.auth.command.OAuth2LogoutCommand
import com.advisor.api.iam.port.inbound.auth.command.RenewTokenCommand
import com.advisor.api.iam.port.inbound.auth.result.OAuth2LogoutUseCase
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val renewTokenUseCase: RenewTokenUseCase,
    private val oAuth2LogoutUseCase: OAuth2LogoutUseCase,
    private val cookieManager: CookieManager
) {
    @GetMapping("/refresh")
    fun renewToken(
        @CookieValue("refreshToken") refreshToken: String,
        @AuthenticationPrincipal member: CustomUserDetails,
        response: HttpServletResponse
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = RenewTokenCommand(
            refreshToken = refreshToken,
            memberId = member.id
        )

        val result = renewTokenUseCase.execute(command)

        cookieManager.setAccessTokenCookie(response, result.accessToken)
        cookieManager.setRefreshTokenCookie(response, result.refreshToken)

        val apiResponse = BaseApiResponse<Unit>(
            success = true,
            message = "토큰 갱신 성공",
            httpStatus = HttpStatus.CREATED,
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
    }

    @GetMapping("/logout")
    fun logout(
        @AuthenticationPrincipal member: CustomUserDetails,
        response: HttpServletResponse
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = OAuth2LogoutCommand(
            memberId = member.id
        )
        oAuth2LogoutUseCase.execute(command)

        cookieManager.deleteCookie(response, "accessToken")
        cookieManager.deleteCookie(response, "refreshToken")

        val apiResponse = BaseApiResponse<Unit>(
            success = true,
            message = "로그아웃 성공",
            httpStatus = HttpStatus.OK,
        )

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse)
    }
}
