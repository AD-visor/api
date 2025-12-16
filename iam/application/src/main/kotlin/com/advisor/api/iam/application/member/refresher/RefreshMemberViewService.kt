package com.advisor.api.iam.application.member.refresher

import com.advisor.api.iam.domain.member.MemberReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
internal class RefreshMemberViewService(
    private val memberReader: MemberReader
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun execute() {
        memberReader.refreshView()
    }
}
