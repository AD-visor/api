package com.advisor.api.iam.port.inbound.member.result

import com.advisor.api.iam.domain.member.MemberView
import java.time.Instant

data class GetMemberResult(
    val id: Long,
    val email: String,
    val createdAt: Instant,
) {
    companion object {
        fun fromModel(model: MemberView): GetMemberResult {
            return GetMemberResult(
                id = model.id,
                email = model.email,
                createdAt = model.createdAt,
            )
        }
    }
}
