package com.advisor.api.iam.application.member

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.infrastructure.DomainEventPublisher
import com.advisor.api.iam.port.inbound.member.DeleteMemberUseCase
import com.advisor.api.iam.port.inbound.member.command.DeleteMemberCommand
import com.advisor.api.iam.port.outbound.member.MemberStore
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

        domainEventPublisher.publishFrom(updatedMember)
    }
}
