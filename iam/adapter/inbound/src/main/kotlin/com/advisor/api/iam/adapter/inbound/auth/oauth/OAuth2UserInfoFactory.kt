package com.advisor.api.iam.adapter.inbound.auth.oauth

import com.advisor.api.iam.adapter.inbound.auth.oauth.kakao.KakaoOAuth2UserInfo
import org.springframework.stereotype.Component

@Component
class OAuth2UserInfoFactory {
    fun of(provider: String, attributes: Map<String, Any>): OAuth2UserInfo {
        return when (provider) {
            "kakao" -> KakaoOAuth2UserInfo(attributes)
            //"google" -> GoogleOAuth2UserInfo(attributes)
            //"naver" -> NaverOAuth2UserInfo(attributes)
            else -> throw IllegalArgumentException("Unsupported provider: $provider")
        }
    }
}
