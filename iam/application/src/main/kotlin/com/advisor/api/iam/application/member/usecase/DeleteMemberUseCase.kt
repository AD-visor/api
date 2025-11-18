package com.advisor.api.iam.application.member.usecase

import com.advisor.api.iam.application.member.command.DeleteMemberCommand

interface DeleteMemberUseCase {
    fun execute(command: DeleteMemberCommand) {}
}
