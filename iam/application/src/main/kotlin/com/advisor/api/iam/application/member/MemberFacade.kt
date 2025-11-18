package com.advisor.api.iam.application.member

import com.advisor.api.iam.application.member.command.CreateMemberCommand
import com.advisor.api.iam.application.member.command.DeleteMemberCommand
import com.advisor.api.iam.application.member.query.GetMemberQuery
import com.advisor.api.iam.application.member.usecase.CreateMemberUseCase
import com.advisor.api.iam.application.member.usecase.DeleteMemberUseCase
import com.advisor.api.iam.application.member.usecase.GetMemberUseCase
import com.advisor.api.iam.domain.member.MemberView
import org.springframework.stereotype.Service

@Service
class MemberFacade(
    private val createMemberUseCase: CreateMemberUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase,
    private val getMemberUseCase: GetMemberUseCase
) {
    fun createMember(command: CreateMemberCommand) {
        createMemberUseCase.execute(command)
    }

    fun deleteMember(command: DeleteMemberCommand) {
        deleteMemberUseCase.execute(command)
    }

    fun getMember(query: GetMemberQuery): MemberView {
        return getMemberUseCase.execute(query)
    }
}
