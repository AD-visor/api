package com.advisor.api.iam.port.inbound.auth.result

import com.advisor.api.iam.port.inbound.auth.command.OAuth2LogoutCommand

interface OAuth2LogoutUseCase {
    fun execute(command: OAuth2LogoutCommand)
}
