package com.advisor.api.iam.port.outbound.auth

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.iam.domain.auth.Auth

interface AuthStore {
    fun save(auth: Auth)
    fun loadByMemberId(memberId: MemberId): Auth
    fun loadByProviderAndOAuthId(providerName: String, oAuthId: String): Auth?
}
