package com.advisor.api.iam.domain.member

import com.advisor.api.common.core.domain.vo.identifier.MemberId

interface MemberStore {
    fun save(member: Member)
    fun loadById(id: MemberId): Member
    fun findById(id: MemberId): Member?
}
