package com.advisor.api.iam.domain.member

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.iam.domain.member.vo.Email
import java.time.Instant

class Member(
    id: MemberId,
    private val props: MemberProps,
): AggregateRoot<MemberId>(id) {
    init { validate() }

    companion object {
        fun create(id: MemberId, props: MemberProps): Member {
            return Member(id, props)
        }

        fun of (id: MemberId, props: MemberProps): Member {
            return Member(id, props)
        }
    }

    fun delete(): Member {
        return Member(id, props.copy(
            isDeleted = true,
            deletedAt = Instant.now()
        ))
    }

    fun unDelete(): Member {
        return Member(id, props.copy(
            isDeleted = false,
            deletedAt = null
        ))
    }

    private fun validate() {}

    val email: Email get() = props.email
    val createdAt: Instant get() = props.createdAt
    val updatedAt: Instant get() = props.updatedAt
    val isDeleted: Boolean get() = props.isDeleted
    val deletedAt: Instant? get() = props.deletedAt
}
