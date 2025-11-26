package com.advisor.api.iam.domain.auth.vo

enum class JwtTokenType(val value: String) {
    ACCESS("access"),
    REFRESH("refresh");
}
