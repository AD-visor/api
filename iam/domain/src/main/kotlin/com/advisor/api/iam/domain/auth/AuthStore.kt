package com.advisor.api.iam.domain.auth

import com.advisor.api.common.core.domain.vo.identifier.MemberId

interface AuthStore {
    fun save(auth: Auth)
    fun loadByMemberId(memberId: MemberId): Auth
    fun loadByProviderAndOAuthId(providerName: String, oAuthId: String): Auth?
}
