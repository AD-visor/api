package com.advisor.api.iam.port.outbound.member

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.iam.domain.member.Member

interface MemberStore {
    fun save(member: Member)
    fun loadById(id: MemberId): Member
    fun findById(id: MemberId): Member?
}
