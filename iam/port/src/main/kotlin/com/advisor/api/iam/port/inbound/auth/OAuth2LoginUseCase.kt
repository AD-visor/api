package com.advisor.api.iam.port.inbound.auth

import com.advisor.api.iam.port.inbound.auth.command.OAuth2LoginCommand
import com.advisor.api.iam.port.inbound.auth.result.OAuth2LoginResult

interface OAuth2LoginUseCase {
    fun execute(command: OAuth2LoginCommand): OAuth2LoginResult
}
