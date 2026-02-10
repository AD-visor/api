package com.advisor.api.conversation.domain.conversation

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class ConversationDomainExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    CONVERSATION_BUSINESS_TYPE_BLANK(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_BUSINESS_TYPE_BLANK",
        message = "[Conversation] 비즈니스 유형은 공백일 수 없습니다."
    ),
    CONVERSATION_BUSINESS_TYPE_LENGTH_EXCEEDED(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_BUSINESS_TYPE_LENGTH_EXCEEDED",
        message = "[Conversation] 비즈니스 유형은 최대 30자 입니다."
    ),
    CONVERSATION_PRODUCT_NAME_BLANK(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_PRODUCT_NAME_BLANK",
        message = "[Conversation] 상품/서비스명은 공백일 수 없습니다."
    ),
    CONVERSATION_PRODUCT_NAME_LENGTH_EXCEEDED(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_PRODUCT_NAME_LENGTH_EXCEEDED",
        message = "[Conversation] 상품/서비스명은 최대 30자 입니다."
    ),
    CONVERSATION_DESCRIPTION_LENGTH_EXCEEDED(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_DESCRIPTION_LENGTH_EXCEEDED",
        message = "[Conversation] 설명은 최대 1000자 입니다."
    ),
    CONVERSATION_TARGET_AUDIENCE_LENGTH_EXCEEDED(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_TARGET_AUDIENCE_LENGTH_EXCEEDED",
        message = "[Conversation] 타겟 고객은 최대 200자 입니다."
    ),
    CONVERSATION_MESSAGE_BODY_LENGTH_EXCEEDED(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_MESSAGE_BODY_LENGTH_EXCEEDED",
        message = "[Conversation] 메시지 본문은 최대 5000자 입니다."
    ),
    CONVERSATION_PLATFORM_INVALID(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_PLATFORM_INVALID",
        message = "[Conversation] 유효하지 않은 콘텐츠 플랫폼입니다."
    ),
    CONVERSATION_INVALID_MESSAGE_ROLE(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_INVALID_MESSAGE_ROLE",
        message = "[Conversation] 유효하지 않은 메시지 타입입니다."
    ),
    CONVERSATION_TONE_STYLE_INVALID(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_TONE_STYLE_INVALID",
        message = "[Conversation] 유효하지 않은 톤앤스타일입니다."
    ),
    CONVERSATION_SPEECH_STYLE_INVALID(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_SPEECH_STYLE_INVALID",
        message = "[Conversation] 유효하지 않은 스피치스타일입니다."
    ),
    CONVERSATION_INVALID_MESSAGE_STATUS(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_INVALID_MESSAGE_STATUS",
        message = "[Conversation] 유효하지 않은 메시지 상태입니다."
    ),
    CONVERSATION_INVALID_MESSAGE_STATUS_TRANSITION(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONVERSATION_INVALID_MESSAGE_STATUS_TRANSITION",
        message = "[Conversation] 메시지 상태 전이가 불가능합니다."
    ),
}
