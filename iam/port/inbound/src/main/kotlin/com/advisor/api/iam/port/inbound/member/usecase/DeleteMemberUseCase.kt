package com.advisor.api.iam.port.inbound.member.usecase

import com.advisor.api.iam.port.inbound.member.command.DeleteMemberCommand

interface DeleteMemberUseCase {
    fun execute(command: DeleteMemberCommand)
}
