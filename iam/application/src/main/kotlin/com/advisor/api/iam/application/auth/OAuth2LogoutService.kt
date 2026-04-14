package com.advisor.api.iam.application.auth

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.iam.port.outbound.auth.AuthStore
import com.advisor.api.iam.port.inbound.auth.command.OAuth2LogoutCommand
import com.advisor.api.iam.port.inbound.auth.OAuth2LogoutUseCase
import org.springframework.stereotype.Service

@Service
class OAuth2LogoutService(
    private val authStore: AuthStore
): OAuth2LogoutUseCase {
    override fun execute(command: OAuth2LogoutCommand) {
        val memberId = MemberId(command.memberId)
        val auth = authStore.loadByMemberId(memberId)

        val updatedAuth = auth.logout()
        println("RefreshToken:" + updatedAuth.refreshToken?.token)

        authStore.save(updatedAuth)
    }
}