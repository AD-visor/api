package com.advisor.api.token_usage.domain.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.token_usage.domain.TokenUsageDomainExceptionCode

class SourceContext private constructor(val value: String) {
    init { validate() }

    companion object {
        val CONTENT_REQUEST = SourceContext("CONTENT_REQUEST")
        val CONTENT_REVISION = SourceContext("CONTENT_REVISION")
        val CONTENT = SourceContext("CONTENT")

        val allowedSourceContexts = setOf(CONTENT_REQUEST, CONTENT_REVISION, CONTENT)

        fun create(value: String): SourceContext {
            return when (value) {
                "CONTENT_REQUEST" -> CONTENT_REQUEST
                "CONTENT_REVISION" -> CONTENT_REVISION
                "CONTENT" -> CONTENT
                else -> throw CustomException(
                    TokenUsageDomainExceptionCode.TOKEN_USAGE_INVALID_SOURCE_CONTEXT,
                    "[TokenUsage] 유효하지 않은 사용처입니다."
                )
            }
        }
    }

    private fun validate() {}

    override fun equals(other: Any?): Boolean = other is SourceContext && this.value == other.value
    override fun hashCode(): Int = value.hashCode()
}
