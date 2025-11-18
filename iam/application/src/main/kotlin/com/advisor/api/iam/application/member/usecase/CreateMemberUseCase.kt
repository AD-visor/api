package com.advisor.api.iam.application.member.usecase

import com.advisor.api.iam.application.member.command.CreateMemberCommand

interface CreateMemberUseCase {
    fun execute(command: CreateMemberCommand)
}
