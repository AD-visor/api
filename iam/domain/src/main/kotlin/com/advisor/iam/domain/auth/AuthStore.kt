package com.advisor.iam.domain.auth

import com.advisor.api.common.core.domain.vo.identifier.MemberId

interface AuthStore {
    fun save(auth: Auth)
    fun load(id: MemberId): Auth
}
