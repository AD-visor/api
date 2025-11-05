package com.advisor.api.iam.application.member.command

data class CreateMemberCommand(
    val memberId: Long,
    val email: String
)
