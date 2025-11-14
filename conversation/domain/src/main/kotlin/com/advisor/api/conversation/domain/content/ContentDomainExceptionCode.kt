package com.advisor.api.conversation.domain.content

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class ContentDomainExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    CONTENT_TITLE_BLANK(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONTENT_TITLE_BLANK",
        message = "[Content] 제목은 공백일 수 없습니다."
    ),
    CONTENT_TITLE_LENGTH_EXCEEDED(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONTENT_TITLE_EXCEED_LENGTH",
        message = "[Content] 제목은 최대 100자 입니다."
    ),
    CONTENT_BODY_LENGTH_EXCEEDED(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONTENT_BODY_EXCEED_LENGTH",
        message = "[Content] 본문은 최대 5000자 입니다."
    ),
    CONTENT_PLATFORM_INVALID(
        status = ErrorStatus.BAD_REQUEST,
        code = "CONTENT_PLATFORM_INVALID",
        message = "[Content] 유효하지 않은 콘텐츠 플랫폼입니다."
    ),
}
