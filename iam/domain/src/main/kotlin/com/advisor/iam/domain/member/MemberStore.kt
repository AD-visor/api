package com.advisor.iam.domain.member

import com.advisor.api.common.core.domain.vo.identifier.MemberId

interface MemberStore {
    fun save(member: Member)
    fun load(id: MemberId): Member
}
