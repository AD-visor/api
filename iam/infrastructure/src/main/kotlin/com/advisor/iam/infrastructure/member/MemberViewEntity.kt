package com.advisor.iam.infrastructure.member

import com.advisor.iam.domain.member.MemberView
import java.time.Instant

class MemberViewEntity(
    val id: Long,
    val email: String,
    val createdAt: Instant,
) {
    companion object {
        fun toModel(entity: MemberViewEntity): MemberView{
            return MemberView(
                id = entity.id,
                email = entity.email,
                createdAt = entity.createdAt,
            )
        }

        fun toPersistence(model: MemberViewEntity): MemberViewEntity {
            return MemberViewEntity(
                id = model.id,
                email = model.email,
                createdAt = model.createdAt,
            )
        }
    }
}
