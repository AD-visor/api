package com.advisor.api.iam.application.member

import com.advisor.api.iam.port.inbound.member.query.GetMemberQuery
import com.advisor.api.iam.port.outbound.member.MemberReader
import com.advisor.api.iam.port.inbound.member.GetMemberUseCase
import com.advisor.api.iam.port.inbound.member.view.MemberView
import org.springframework.stereotype.Service

@Service
class GetMemberService(
    private val memberReader: MemberReader
): GetMemberUseCase {
    override fun execute(query: GetMemberQuery): MemberView {
        return memberReader.findById(query.id)
    }
}
