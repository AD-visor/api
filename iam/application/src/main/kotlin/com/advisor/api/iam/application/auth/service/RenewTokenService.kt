package com.advisor.api.iam.application.auth.service

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.iam.domain.auth.Auth
import com.advisor.api.iam.domain.auth.AuthStore
import com.advisor.api.iam.domain.auth.vo.RefreshToken
import com.advisor.api.iam.domain.auth.vo.RefreshTokenProps
import com.advisor.api.iam.port.inbound.auth.RenewTokenUseCase
import com.advisor.api.iam.port.inbound.auth.command.RenewTokenCommand
import com.advisor.api.iam.port.inbound.auth.result.RenewTokenResult
import com.advisor.api.iam.port.outbound.auth.AuthTokenPort
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RenewTokenService(
    private val authStore: AuthStore,
    private val authTokenPort: AuthTokenPort
): RenewTokenUseCase {
    override fun execute(command: RenewTokenCommand): RenewTokenResult {
        val memberId = MemberId(command.memberId)
        val auth = validateRefreshToken(memberId, command.refreshToken)

        val (accessToken, refreshToken) = generateTokens(memberId)

        updateRefreshToken(auth, refreshToken)

        val result = RenewTokenResult(
            accessToken = accessToken,
            refreshToken = refreshToken
        )

        return result
    }

    private fun validateRefreshToken(memberId: MemberId, refreshToken: String): Auth {
        val auth = authStore.loadByMemberId(memberId)

        if (auth.refreshToken == null) {
            throw CustomException(
                AuthApplicationExceptionCode.AUTH_MISSING_REFRESH_TOKEN,
                "[Auth] 리프레시 토큰이 존재하지 않습니다."
            )
        }

        if (auth.refreshToken?.token != refreshToken) {
            throw CustomException(
                AuthApplicationExceptionCode.AUTH_INVALID_REFRESH_TOKEN,
                "[Auth] 리프레시 토큰이 유효하지 않습니다."
            )
        }

        return auth
    }

    private fun generateTokens(memberId: MemberId): Pair<String, String>  {
        val accessToken = authTokenPort.generateAccessToken(memberId.value.toString(), emptyMap())
        val refreshToken = authTokenPort.generateRefreshToken(memberId.value.toString(), emptyMap())

        return Pair(accessToken, refreshToken)
    }

    private fun updateRefreshToken(auth: Auth, refreshToken: String) {
        val refreshTokenObject = RefreshToken.create(
            RefreshTokenProps(
                token = refreshToken,
                jti = "a",
                createdAt = Instant.now(),
                expiresAt = Instant.now().plusSeconds(2592000)
            )
        )

        val updatedAuth = auth.updateRefreshToken(refreshTokenObject)

        authStore.save(updatedAuth)
    }
}
