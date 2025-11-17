package com.advisor.api.iam.application.member.service

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.iam.application.member.command.CreateMemberCommand
import com.advisor.api.iam.application.member.usecase.CreateMemberUseCase
import com.advisor.api.iam.domain.member.Member
import com.advisor.api.iam.domain.member.MemberProps
import com.advisor.api.iam.domain.member.MemberStore
import com.advisor.api.iam.domain.member.vo.Email
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class CreateMemberService(
    private val memberStore: MemberStore,
): CreateMemberUseCase {
    override fun execute(command: CreateMemberCommand) {
        val existingMember = memberStore.findById(MemberId(command.memberId))
        if (existingMember !== null && existingMember.isDeleted) unDelete(existingMember)
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
    }

    private fun unDelete(existingMember: Member) {
        val updatedMember = existingMember.unDelete()
        memberStore.save(updatedMember)
    }
}
