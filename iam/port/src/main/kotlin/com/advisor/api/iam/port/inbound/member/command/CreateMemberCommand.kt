package com.advisor.api.iam.port.inbound.member.command

data class CreateMemberCommand(
    val memberId: Long,
    val email: String
)
