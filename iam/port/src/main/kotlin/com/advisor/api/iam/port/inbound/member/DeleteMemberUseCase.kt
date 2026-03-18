package com.advisor.api.iam.port.inbound.member

import com.advisor.api.iam.port.inbound.member.command.DeleteMemberCommand

interface DeleteMemberUseCase {
    fun execute(command: DeleteMemberCommand)
}
