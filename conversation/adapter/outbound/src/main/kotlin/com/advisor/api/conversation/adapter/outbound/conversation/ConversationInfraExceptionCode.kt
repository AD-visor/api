package com.advisor.api.conversation.adapter.outbound.conversation

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class ConversationInfraExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    CONVERSATION_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        code = "CONVERSATION_NOT_FOUND",
        message = "[Conversation] 해당 대화를 찾을 수 없습니다."
    ),
}
