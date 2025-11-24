package com.advisor.api.iam.application.auth.service

import com.advisor.api.common.core.domain.vo.identifier.AuthId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.infrastructure.SnowFlakeIdUtil
import com.advisor.api.iam.domain.auth.Auth
import com.advisor.api.iam.domain.auth.AuthProps
import com.advisor.api.iam.domain.auth.AuthStore
import com.advisor.api.iam.domain.auth.vo.*
import com.advisor.api.iam.port.inbound.auth.OAuth2LoginUseCase
import com.advisor.api.iam.port.inbound.auth.command.OAuth2LoginCommand
import com.advisor.api.iam.port.inbound.auth.result.OAuth2LoginResult
import com.advisor.api.iam.port.inbound.member.command.CreateMemberCommand
import com.advisor.api.iam.port.inbound.member.usecase.CreateMemberUseCase
import com.advisor.api.iam.port.outbound.auth.AuthTokenPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class OAuth2LoginService(
    private val authStore: AuthStore,
    private val authTokenPort: AuthTokenPort,
    private val snowFlakeIdUtil: SnowFlakeIdUtil,
    private val createMemberUseCase: CreateMemberUseCase
) : OAuth2LoginUseCase {
    @Transactional
    override fun execute(command: OAuth2LoginCommand): OAuth2LoginResult {
        val auth = findOrCreateAuth(
            memberId = MemberId(snowFlakeIdUtil.generateId()),
            provider = command.providerName,
            oAuthId = command.oAuthId,
            email = command.email
        )

        val (accessToken, refreshToken) = generateTokens(auth.memberId)
        updateRefreshToken(auth, refreshToken)

        val result = OAuth2LoginResult(
            accessToken = accessToken,
            refreshToken = refreshToken
        )

        return result
    }

    private fun findOrCreateAuth(
        memberId: MemberId,
        provider: String,
        oAuthId: String,
        email: String
    ): Auth {
        val existingAuth = authStore.loadByProviderAndOAuthId(provider, oAuthId)
        if (existingAuth != null) return existingAuth

        val authProps = AuthProps(
            memberId = memberId,
            oAuthCredential = OAuthCredential.create(
                OAuthCredentialProps(
                    provider = OAuthProvider.fromString(provider),
                    oAuthId = oAuthId,
                    accessToken = null
                )
            ),
            refreshToken = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            isDeleted = false,
            deletedAt = null
        )

        val auth = Auth.create(AuthId(snowFlakeIdUtil.generateId()), authProps)
        createNewMember(memberId, email)

        authStore.save(auth)

        return auth
    }

    private fun createNewMember(memberId: MemberId, email: String) {
        val command = CreateMemberCommand(
            memberId = memberId.value,
            email = email
        )
        createMemberUseCase.execute(command)
    }

    private fun updateRefreshToken(auth: Auth, refreshToken: String) {
        val refreshTokenObject = RefreshToken.create(RefreshTokenProps(
            token = refreshToken,
            jti = "a",
            createdAt = Instant.now(),
            expiresAt = Instant.now().plusSeconds(2592000)
        ))

        val updatedAuth = auth.updateRefreshToken(refreshTokenObject)

        authStore.save(updatedAuth)
    }

    private fun generateTokens(memberId: MemberId): Pair<String, String> {
        val accessToken = authTokenPort.generateAccessToken(memberId.value.toString(), emptyMap())
        val refreshToken = authTokenPort.generateRefreshToken(memberId.value.toString(), emptyMap())

        return Pair(accessToken, refreshToken)
    }
}
