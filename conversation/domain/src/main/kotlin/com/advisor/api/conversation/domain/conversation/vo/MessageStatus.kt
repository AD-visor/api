package com.advisor.api.conversation.domain.conversation.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.conversation.ConversationDomainExceptionCode

data class MessageStatus(val value: String) {
    init {
        validate()
    }

    companion object {
        private val STATE_ORDER = mapOf(
            "PENDING" to 0,
            "PROCESSING" to 1,
            "COMPLETED" to 2,
            "FAILED" to -1
        )

        val PENDING = MessageStatus("PENDING")
        val PROCESSING = MessageStatus("PROCESSING")
        val COMPLETED = MessageStatus("COMPLETED")
        val FAILED = MessageStatus("FAILED")

        fun create(value: String): MessageStatus {
            return when (value) {
                "PENDING" -> PENDING
                "PROCESSING" -> PROCESSING
                "COMPLETED" -> COMPLETED
                "FAILED" -> FAILED
                else -> throw CustomException(
                    ConversationDomainExceptionCode.CONVERSATION_INVALID_MESSAGE_STATUS,
                    "[Conversation] 유효하지 않은 메시지 상태입니다: $value"
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
        if (this == COMPLETED || this == FAILED) {
            throw CustomException(
                ConversationDomainExceptionCode.CONVERSATION_INVALID_MESSAGE_STATUS_TRANSITION,
                "[Conversation] 종료된 메시지 상태(${this.value})에서는 상태 전이가 불가능합니다."
            )
        }

        // 4. 순차 진행 보장 (현재 단계보다 뒤에 있는 단계로만 전이 가능)
        val currentOrder = STATE_ORDER[this.value]
            ?: throw CustomException(
                ConversationDomainExceptionCode.CONVERSATION_INVALID_MESSAGE_STATUS,
                "[Conversation] 유효하지 않은 현재 상태입니다: ${this.value}"
            )

        val nextOrder = STATE_ORDER[next.value]
            ?: throw CustomException(
                ConversationDomainExceptionCode.CONVERSATION_INVALID_MESSAGE_STATUS,
                "[Conversation] 유효하지 않은 다음 상태입니다: ${next.value}"
            )

        return nextOrder > currentOrder
    }

    private fun validate() {
        if (value !in STATE_ORDER.keys) {
            throw CustomException(
                ConversationDomainExceptionCode.CONVERSATION_INVALID_MESSAGE_STATUS,
                "[Conversation] 유효하지 않은 메시지 상태입니다: $value"
            )
        }
    }

    override fun equals(other: Any?): Boolean =
        other is MessageStatus && this.value == other.value

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = value
}
