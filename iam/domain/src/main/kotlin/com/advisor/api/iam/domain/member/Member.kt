package com.advisor.api.iam.domain.member

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.iam.domain.member.event.MemberCreatedEvent
import com.advisor.api.iam.domain.member.event.MemberDeletedEvent
import com.advisor.api.iam.domain.member.vo.Email
import java.time.Instant

class Member private constructor(
    id: MemberId,
    private val props: MemberProps,
): AggregateRoot<MemberId>(id) {
    init { validate() }

    companion object {
        fun create(id: MemberId, props: MemberProps): Member {
            val member = Member(id, props)
            member.addDomainEvent(MemberCreatedEvent())

            return member
        }

        fun of (id: MemberId, props: MemberProps): Member {
            return Member(id, props)
        }
    }

    fun delete(): Member {
        val member = Member(id, props.copy(
            isDeleted = true,
            deletedAt = Instant.now()
        ))

        member.addDomainEvent(MemberDeletedEvent())

        return member
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
