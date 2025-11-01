package com.advisor.iam.domain.auth

import com.advisor.api.common.core.domain.vo.identifier.MemberId

interface AuthReader {
    fun findByMemberId(memberId: MemberId): AuthView
}
