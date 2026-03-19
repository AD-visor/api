package com.advisor.api.iam.port.inbound.member

import com.advisor.api.iam.port.inbound.member.command.WithdrawMemberCommand

interface WithdrawMemberUseCase {
    fun execute(command: WithdrawMemberCommand)
}
