package com.advisor.api.iam.application.auth

import com.advisor.api.common.core.domain.vo.identifier.AuthId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.infrastructure.SnowFlakeIdUtil
import com.advisor.api.iam.domain.auth.Auth
import com.advisor.api.iam.domain.auth.AuthProps
import com.advisor.api.iam.port.outbound.auth.AuthStore
import com.advisor.api.iam.domain.auth.vo.*
import com.advisor.api.iam.port.inbound.auth.OAuth2LoginUseCase
import com.advisor.api.iam.port.inbound.auth.command.OAuth2LoginCommand
import com.advisor.api.iam.port.inbound.auth.result.OAuth2LoginResult
import com.advisor.api.iam.port.inbound.member.CreateMemberUseCase
import com.advisor.api.iam.port.inbound.member.command.CreateMemberCommand
import com.advisor.api.iam.port.outbound.auth.AuthTokenPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class OAuth2LoginService(
    private val createMemberUseCase: CreateMemberUseCase,
    private val authStore: AuthStore,
    private val authTokenPort: AuthTokenPort,
    private val snowFlakeIdUtil: SnowFlakeIdUtil
) : OAuth2LoginUseCase {
    @Transactional
    override fun execute(command: OAuth2LoginCommand): OAuth2LoginResult {
        val auth = findOrCreateAuth(
            provider = command.providerName,
            oAuthId = command.oAuthId
        )

        createNewMember(auth.memberId, command.email)

        val (accessToken, refreshToken) = generateTokens(auth.memberId)
        updateRefreshToken(auth, refreshToken)

        val result = OAuth2LoginResult(
            accessToken = accessToken,
            refreshToken = refreshToken
        )

        return result
    }

    private fun findOrCreateAuth(
        provider: String,
        oAuthId: String
    ): Auth {
        val existingAuth = authStore.loadByProviderAndOAuthId(provider, oAuthId)
        existingAuth?.let { return if (it.isDeleted) unDeleteAuth(it) else it }

        return createNewAuth(
            memberId = MemberId(snowFlakeIdUtil.generateId()),
            provider = provider,
            oAuthId = oAuthId
        )
    }

    private fun createNewAuth(memberId: MemberId, provider: String, oAuthId: String): Auth {
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

        authStore.save(auth)

        return auth
    }

    private fun unDeleteAuth(existingAuth: Auth): Auth {
        val updatedAuth = existingAuth.unDelete()
        authStore.save(updatedAuth)

        return updatedAuth
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
