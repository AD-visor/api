package com.advisor.api.conversation.domain.conversation.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.conversation.ConversationDomainExceptionCode

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
                    ConversationDomainExceptionCode.CONVERSATION_PLATFORM_INVALID,
                    "[Conversation] 유효하지 않은 콘텐츠 플랫폼입니다."
                )
            }
        }
    }

    private fun validate() {}

    override fun equals(other: Any?): Boolean = other is ContentPlatform && this.value == other.value
    override fun hashCode(): Int = value.hashCode()

}
