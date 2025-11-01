package com.advisor.iam.infrastructure.member

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.iam.domain.member.Member
import com.advisor.iam.domain.member.MemberProps
import com.advisor.iam.domain.member.vo.Email
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
        fun toDomain(jpaEntity: MemberEntity): Member {
            val memberProps = MemberProps(
                email = Email.create(jpaEntity.email),
                createdAt = jpaEntity.createdAt,
                updatedAt = jpaEntity.updatedAt,
                isDeleted = jpaEntity.isDeleted,
                deletedAt = jpaEntity.deletedAt
            )

            return Member.of(MemberId(jpaEntity.id), memberProps)
        }

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
}
