package com.advisor.api.iam.port.inbound.auth

import com.advisor.api.iam.port.inbound.auth.command.DeleteAuthCommand

interface DeleteAuthUseCase {
    fun execute(command: DeleteAuthCommand)
}
