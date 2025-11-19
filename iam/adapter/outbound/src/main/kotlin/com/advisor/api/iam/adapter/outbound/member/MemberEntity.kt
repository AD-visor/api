package com.advisor.api.iam.adapter.outbound.member

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.iam.domain.member.Member
import com.advisor.api.iam.domain.member.MemberProps
import com.advisor.api.iam.domain.member.vo.Email
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "member")
class MemberEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val updatedAt: Instant,

    @Column(nullable = false)
    val isDeleted: Boolean,

    @Column
    val deletedAt: Instant?,
) {
    companion object {
        fun toPersistence(domain: Member): MemberEntity {
            return MemberEntity(
                id = domain.id.value,
                email = domain.email.value,
                createdAt = domain.createdAt,
                updatedAt = domain.updatedAt,
                isDeleted = domain.isDeleted,
                deletedAt = domain.deletedAt
            )
        }
    }

    fun toDomain(): Member {
        val memberProps = MemberProps(
            email = Email.create(email),
            createdAt = createdAt,
            updatedAt = updatedAt,
            isDeleted = isDeleted,
            deletedAt = deletedAt
        )

        return Member.of(MemberId(id), memberProps)
    }
}
