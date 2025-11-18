package com.advisor.api.iam.application.member.service

import com.advisor.api.iam.application.member.query.GetMemberQuery
import com.advisor.api.iam.application.member.usecase.GetMemberUseCase
import com.advisor.api.iam.domain.member.MemberReader
import com.advisor.api.iam.domain.member.MemberView
import org.springframework.stereotype.Service

@Service
class GetMemberService(
    private val memberReader: MemberReader
): GetMemberUseCase {
    override fun execute(query: GetMemberQuery): MemberView {
        return memberReader.findById(query.id)
    }
}
