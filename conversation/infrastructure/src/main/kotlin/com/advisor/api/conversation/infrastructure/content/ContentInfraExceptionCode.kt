package com.advisor.api.conversation.infrastructure.content

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class ContentInfraExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    CONTENT_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        code = "CONTENT_NOT_FOUND",
        message = "[Content] 해당 콘텐츠를 찾을 수 없습니다."
    ),
}
