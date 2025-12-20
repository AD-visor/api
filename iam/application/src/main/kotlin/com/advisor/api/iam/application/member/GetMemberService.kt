package com.advisor.api.iam.application.member

import com.advisor.api.iam.port.inbound.member.query.GetMemberQuery
import com.advisor.api.iam.domain.member.MemberReader
import com.advisor.api.iam.port.inbound.member.result.GetMemberResult
import com.advisor.api.iam.port.inbound.member.usecase.GetMemberUseCase
import org.springframework.stereotype.Service

@Service
class GetMemberService(
    private val memberReader: MemberReader
): GetMemberUseCase {
    override fun execute(query: GetMemberQuery): GetMemberResult {
        return GetMemberResult.fromModel(memberReader.findById(query.id))
    }
}
