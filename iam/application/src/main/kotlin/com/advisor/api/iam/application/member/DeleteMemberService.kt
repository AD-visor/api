package com.advisor.api.iam.application.member

import com.advisor.api.common.core.domain.DomainEventPublisher
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.iam.port.inbound.member.command.DeleteMemberCommand
import com.advisor.api.iam.domain.member.MemberStore
import com.advisor.api.iam.port.inbound.member.usecase.DeleteMemberUseCase
import org.springframework.stereotype.Service

@Service
class DeleteMemberService(
    private val memberStore: MemberStore,
    private val domainEventPublisher: DomainEventPublisher
): DeleteMemberUseCase {
    override fun execute(command: DeleteMemberCommand) {
        val member = memberStore.loadById(MemberId(command.id))
        val updatedMember = member.delete()

        memberStore.save(updatedMember)

        domainEventPublisher.publish(updatedMember)
    }
}
