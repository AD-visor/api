package com.advisor.api.iam.port.inbound.member.usecase

import com.advisor.api.iam.port.inbound.member.command.CreateMemberCommand

interface CreateMemberUseCase {
    fun execute(command: CreateMemberCommand)
}
