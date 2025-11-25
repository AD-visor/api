package com.advisor.api.iam.application.member.service

import com.advisor.api.iam.domain.member.MemberReader
import com.advisor.api.iam.port.inbound.member.usecase.RefreshMemberViewUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class RefreshMemberViewService(
    private val memberReader: MemberReader
): RefreshMemberViewUseCase {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun execute() {
        memberReader.refreshView()
    }
}
