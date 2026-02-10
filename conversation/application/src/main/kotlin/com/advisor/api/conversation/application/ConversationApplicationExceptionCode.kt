package com.advisor.api.conversation.application

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class ConversationApplicationExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    CONVERSATION_EMPTY_LAYOUT_ANALYSIS_RESPONSE(
        status = ErrorStatus.INTERNAL_ERROR,
        code = "CONVERSATION_EMPTY_LAYOUT_ANALYSIS_RESPONSE",
        message = "[Conversation] 레이아웃 분석 응답이 비어 있습니다."
    ),
    CONVERSATION_LAYOUT_ANALYSIS_FAILURE(
        status = ErrorStatus.INTERNAL_ERROR,
        code = "CONVERSATION_LAYOUT_ANALYSIS_FAILURE",
        message = "[Conversation] 레이아웃 분석에 실패했습니다."
    ),
    CONVERSATION_MESSAGE_REVISION_OF_NOT_FOUND(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_MESSAGE_REVISION_OF_NOT_FOUND",
        message = "[Conversation] 응답 대상 메시지를 찾을 수 없습니다."
    )
}
