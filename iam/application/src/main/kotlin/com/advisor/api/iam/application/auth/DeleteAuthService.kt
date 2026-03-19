package com.advisor.api.iam.application.auth

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.iam.port.outbound.auth.AuthStore
import com.advisor.api.iam.port.inbound.auth.DeleteAuthUseCase
import com.advisor.api.iam.port.inbound.auth.command.DeleteAuthCommand
import org.springframework.stereotype.Service

@Service
class DeleteAuthService(
    private val authStore: AuthStore
): DeleteAuthUseCase {
    override fun execute(command: DeleteAuthCommand) {
        val memberId = MemberId(command.memberId)
        val auth = authStore.loadByMemberId(memberId)

        val updatedAuth = auth.delete()

        authStore.save(updatedAuth)
    }
}
