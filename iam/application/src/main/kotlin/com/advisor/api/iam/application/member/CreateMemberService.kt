package com.advisor.api.iam.application.member

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.infrastructure.DomainEventPublisher
import com.advisor.api.iam.port.inbound.member.command.CreateMemberCommand
import com.advisor.api.iam.domain.member.Member
import com.advisor.api.iam.domain.member.MemberProps
import com.advisor.api.iam.port.outbound.member.MemberStore
import com.advisor.api.iam.domain.member.vo.Email
import com.advisor.api.iam.port.inbound.member.CreateMemberUseCase
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class CreateMemberService(
    private val memberStore: MemberStore,
    private val domainEventPublisher: DomainEventPublisher
): CreateMemberUseCase {
    override fun execute(command: CreateMemberCommand) {
        val existingMember = memberStore.findById(MemberId(command.memberId))
        if (existingMember != null && existingMember.isDeleted) unDelete(existingMember)
        else createNew(command)
    }

    private fun createNew(command: CreateMemberCommand) {
        val memberProps = MemberProps(
            email = Email.create(command.email),
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        val member = Member.create(MemberId(command.memberId), memberProps)

        memberStore.save(member)

        domainEventPublisher.publishFrom(member)
    }

    private fun unDelete(existingMember: Member) {
        val updatedMember = existingMember.unDelete()
        memberStore.save(updatedMember)

        domainEventPublisher.publishFrom(updatedMember)
    }
}
