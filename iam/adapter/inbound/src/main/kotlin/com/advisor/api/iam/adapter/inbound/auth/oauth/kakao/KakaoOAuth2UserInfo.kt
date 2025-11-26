package com.advisor.api.iam.adapter.inbound.auth.oauth.kakao

import com.advisor.api.iam.adapter.inbound.auth.oauth.OAuth2UserInfo

class KakaoOAuth2UserInfo(attributes: Map<String, Any>) : OAuth2UserInfo {
    private val account: Map<*, *> =
        attributes["kakao_account"] as? Map<*, *>
            ?: throw IllegalArgumentException("존재하지 않는 카카오 계정 정보입니다.")

    override val providerName: String = "kakao"

    override val oAuthId: String =
        attributes["id"]?.toString()
            ?: throw IllegalArgumentException("카카오 OAuth ID가 존재하지 않습니다.")

    override val email: String =
        account["email"] as? String
            ?: throw IllegalArgumentException("카카오 이메일이 존재하지 않습니다.")
}
