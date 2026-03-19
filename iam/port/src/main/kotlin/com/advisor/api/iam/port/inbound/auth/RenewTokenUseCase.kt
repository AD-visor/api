package com.advisor.api.iam.port.inbound.auth

import com.advisor.api.iam.port.inbound.auth.command.RenewTokenCommand
import com.advisor.api.iam.port.inbound.auth.result.RenewTokenResult

interface RenewTokenUseCase {
    fun execute(command: RenewTokenCommand): RenewTokenResult
}
