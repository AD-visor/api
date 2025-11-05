package com.advisor.api.iam.presentation.member.dto.request

import com.advisor.api.iam.application.member.command.CreateMemberCommand

data class CreateMemberReqDto(
    val memberId: String,
    val email: String
) {
    fun toCommand(): CreateMemberCommand {
        return CreateMemberCommand(
            memberId = memberId.toLong(),
            email = email
        )
    }
}
