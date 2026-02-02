package com.advisor.api.conversation.domain.conversation.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.conversation.ConversationDomainExceptionCode

data class MessageStatus(val value: String) {
    init { validate() }

    companion object {
        val PENDING = MessageStatus("PENDING")
        val COPY_WRITING = MessageStatus("COPY_WRITING")
        val GENERATING_IMAGE = MessageStatus("GENERATING_IMAGE")
        val ANALYSING_IMAGE = MessageStatus("ANALYSING_IMAGE")
        val COMPOSITING = MessageStatus("COMPOSITING")
        val COMPLETED = MessageStatus("COMPLETED")
        val FAILED = MessageStatus("FAILED")

        private val STATE_ORDER = mapOf(
            "PENDING" to 0,
            "COPY_WRITING" to 1,
            "GENERATING_IMAGE" to 2,
            "ANALYSING_IMAGE" to 3,
            "COMPOSITING" to 4,
            "COMPLETED" to 5,
            "FAILED" to -1
        )

        fun create(value: String): MessageStatus {
            return when (value) {
                "PENDING" -> PENDING
                "COPY_WRITING" -> COPY_WRITING
                "GENERATING_IMAGE" -> GENERATING_IMAGE
                "ANALYSING_IMAGE" -> ANALYSING_IMAGE
                "COMPOSITING" -> COMPOSITING
                "COMPLETED" -> COMPLETED
                "FAILED" -> FAILED
                else -> throw CustomException(
                    ConversationDomainExceptionCode.CONVERSATION_INVALID_MESSAGE_STATUS,
                    "[Conversation] 유효하지 않은 메시지 상태입니다."
                )
            }
        }
    }

    fun canTransitionTo(next: MessageStatus): Boolean {
        // 1. 현재와 동일한 상태로의 전이는 허용 (Idempotency)
        if (this == next) return true

        // 2. 어떤 상태에서든 FAILED로의 전이는 항상 허용
        if (next == FAILED) return true

        // 3. 이미 종료된 상태(COMPLETED, FAILED)에서는 전이 불가
        if (this == COMPLETED || this == FAILED) return false

        // 4. 순차 진행 보장 (현재 단계보다 뒤에 있는 단계로만 전이 가능)
        val currentOrder = STATE_ORDER[this.value] ?: throw CustomException(
            ConversationDomainExceptionCode.CONVERSATION_INVALID_MESSAGE_STATUS,
            "[Conversation] 유효하지 않은 메시지 상태입니다."
        )
        val nextOrder = STATE_ORDER[next.value] ?: throw CustomException(
            ConversationDomainExceptionCode.CONVERSATION_INVALID_MESSAGE_STATUS,
            "[Conversation] 유효하지 않은 메시지 상태입니다."
        )

        return nextOrder > currentOrder
    }

    private fun validate() {}

    override fun equals(other: Any?): Boolean = other is MessageStatus && this.value == other.value
    override fun hashCode(): Int = value.hashCode()
}
