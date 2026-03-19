package com.advisor.api.iam.port.inbound.member

import com.advisor.api.iam.port.inbound.member.query.GetMemberQuery
import com.advisor.api.iam.port.inbound.member.view.MemberView

interface GetMemberUseCase {
    fun execute(query: GetMemberQuery): MemberView
}
