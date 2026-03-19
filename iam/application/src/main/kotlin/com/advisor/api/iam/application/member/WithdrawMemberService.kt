package com.advisor.api.iam.application.member

import com.advisor.api.iam.port.inbound.auth.DeleteAuthUseCase
import com.advisor.api.iam.port.inbound.auth.command.DeleteAuthCommand
import com.advisor.api.iam.port.inbound.member.DeleteMemberUseCase
import com.advisor.api.iam.port.inbound.member.command.DeleteMemberCommand
import com.advisor.api.iam.port.inbound.member.command.WithdrawMemberCommand
import com.advisor.api.iam.port.inbound.member.WithdrawMemberUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WithdrawMemberService(
    private val deleteAuthUseCase: DeleteAuthUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase
): WithdrawMemberUseCase {
    @Transactional
    override fun execute(command: WithdrawMemberCommand) {
        val deleteAuthCommand = DeleteAuthCommand(command.memberId)
        deleteAuthUseCase.execute(deleteAuthCommand)

        val deleteMemberCommand = DeleteMemberCommand(command.memberId)
        deleteMemberUseCase.execute(deleteMemberCommand)
    }
}
