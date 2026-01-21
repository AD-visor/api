package com.advisor.api.media.domain

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class MediaDomainExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    MEDIA_INVALID_MEDIA_TYPE(
        status = ErrorStatus.BAD_REQUEST,
        code = "MEDIA_INVALID_MEDIA_TYPE",
        message = "[Media] 유효하지 않은 미디어 타입입니다."
    ),
    MEDIA_INVALID_MIME_TYPE(
        status = ErrorStatus.BAD_REQUEST,
        code = "MEDIA_INVALID_MIME_TYPE",
        message = "[Media] 유효하지 않은 미디어 MIME 타입입니다."
    ),
    MEDIA_FILE_SIZE_EXCEEDED(
        status = ErrorStatus.BAD_REQUEST,
        code = "MEDIA_FILE_SIZE_EXCEEDED",
        message = "[Media] 파일 크기는 0에서 5MB 사이여야 합니다."
    ),
    MEDIA_PATH_BLANK(
        status = ErrorStatus.BAD_REQUEST,
        code = "MEDIA_PATH_BLANK",
        message = "[Media] 미디어 경로는 비어 있을 수 없습니다."
    ),
}
