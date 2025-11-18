package com.advisor.api.iam.application.member.usecase

import com.advisor.api.iam.application.member.query.GetMemberQuery
import com.advisor.api.iam.domain.member.MemberView

interface GetMemberUseCase {
    fun execute(query: GetMemberQuery): MemberView
}
