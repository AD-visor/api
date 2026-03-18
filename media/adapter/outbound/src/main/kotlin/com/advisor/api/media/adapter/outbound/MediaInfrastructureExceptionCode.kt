package com.advisor.api.media.adapter.outbound

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class MediaInfrastructureExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
) : CustomExceptionCode {
    MEDIA_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        code = "MEDIA_NOT_FOUND",
        message = "[Media] 미디어를 찾을 수 없습니다."
    ),
    MEDIA_FILE_DELETE_FAILURE(
        status = ErrorStatus.INTERNAL_ERROR,
        code = "MEDIA_FILE_DELETE_FAILURE",
        message = "[Media] 미디어 파일 삭제에 실패했습니다."
    ),
    MEDIA_FILE_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        code = "MEDIA_FILE_NOT_FOUND",
        message = "[Media] 미디어 파일을 찾을 수 없습니다."
    ),
    MEDIA_FILE_DOWNLOAD_FAILURE(
        status = ErrorStatus.INTERNAL_ERROR,
        code = "MEDIA_FILE_DOWNLOAD_FAILURE",
        message = "[Media] 미디어 파일 다운로드에 실패했습니다."
    ),
    MEDIA_FILE_SAVE_FAILURE(
        status = ErrorStatus.INTERNAL_ERROR,
        code = "MEDIA_FILE_SAVE_FAILURE",
        message = "[Media] 미디어 파일 저장에 실패했습니다."
    )
}
