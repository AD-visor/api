package com.advisor.api.iam.adapter.inbound.member.dto.response

import com.advisor.api.iam.port.inbound.member.result.GetMemberResult
import java.time.Instant

data class MemberResDto(
    val id: String,
    val email: String,
    val createdAt: Instant
) {
    companion object {
        fun fromResult(result: GetMemberResult): MemberResDto {
            return MemberResDto(
                id = result.id.toString(),
                email = result.email,
                createdAt = result.createdAt
            )
        }
    }
}
