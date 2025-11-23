package com.advisor.api.iam.adapter.inbound.member.dto.request

import com.advisor.api.iam.port.inbound.member.command.CreateMemberCommand

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
