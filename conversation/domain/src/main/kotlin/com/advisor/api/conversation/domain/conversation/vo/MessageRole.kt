package com.advisor.api.conversation.domain.conversation.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.conversation.ConversationDomainExceptionCode

class MessageRole private constructor(val value: String) {
    init { validate() }

    companion object {
        val MEMBER = MessageRole("MEMBER")
        val ASSISTANT = MessageRole("ASSISTANT")

        fun create(value: String): MessageRole {
            return when (value) {
                "MEMBER" -> MEMBER
                "ASSISTANT" -> ASSISTANT
                else -> throw CustomException(
                    ConversationDomainExceptionCode.CONVERSATION_INVALID_MESSAGE_ROLE,
                    "[MessageRole] 유효하지 않은 메시지 타입입니다."
                )
            }
        }
    }

    private fun validate() {}

    override fun equals(other: Any?): Boolean = other is MessageRole && this.value == other.value
    override fun hashCode(): Int = value.hashCode()
}
