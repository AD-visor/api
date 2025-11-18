package com.advisor.api.iam.application.member.service

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.iam.application.member.command.DeleteMemberCommand
import com.advisor.api.iam.application.member.usecase.DeleteMemberUseCase
import com.advisor.api.iam.domain.member.MemberStore
import org.springframework.stereotype.Service

@Service
class DeleteMemberService(
    private val memberStore: MemberStore
): DeleteMemberUseCase {
    override fun execute(command: DeleteMemberCommand) {
        val member = memberStore.loadById(MemberId(command.id))
        val updatedMember = member.delete()

        memberStore.save(updatedMember)
    }
}
