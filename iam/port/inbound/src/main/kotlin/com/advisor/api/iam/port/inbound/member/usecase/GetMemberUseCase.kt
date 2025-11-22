package com.advisor.api.iam.port.inbound.member.usecase

import com.advisor.api.iam.domain.member.MemberView
import com.advisor.api.iam.port.inbound.member.query.GetMemberQuery

interface GetMemberUseCase {
    fun execute(query: GetMemberQuery): MemberView
}
