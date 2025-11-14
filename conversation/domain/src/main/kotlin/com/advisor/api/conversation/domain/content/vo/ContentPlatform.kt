package com.advisor.api.conversation.domain.content.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.content.ContentDomainExceptionCode

class ContentPlatform private constructor(val value: String) {
    init { validate() }

    companion object {
        private val INSTAGRAM = ContentPlatform("INSTAGRAM")
        private val NAVER_BLOG = ContentPlatform("NAVER_BLOG")

        fun create(value: String): ContentPlatform {
            return when (value) {
                "INSTAGRAM" -> INSTAGRAM
                "NAVER_BLOG" -> NAVER_BLOG
                else -> throw CustomException(
                    ContentDomainExceptionCode.CONTENT_PLATFORM_INVALID,
                    "[Content] 유효하지 않은 콘텐츠 플랫폼입니다."
                )
            }
        }
    }

    private fun validate() {}
}
