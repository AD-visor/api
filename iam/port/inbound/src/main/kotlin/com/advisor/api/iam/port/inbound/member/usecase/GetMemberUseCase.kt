package com.advisor.api.iam.port.inbound.member.usecase

import com.advisor.api.iam.port.inbound.member.query.GetMemberQuery
import com.advisor.api.iam.port.inbound.member.result.GetMemberResult

interface GetMemberUseCase {
    fun execute(query: GetMemberQuery): GetMemberResult
}
