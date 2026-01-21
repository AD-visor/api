package com.advisor.api.conversation.adapter.outbound.prompt

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class PromptAdapterExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode  {
    PROMPT_EMPTY_RESPONSE(
        status = ErrorStatus.INTERNAL_ERROR,
        code = "PROMPT_EMPTY_RESPONSE",
        message = "[Prompt] 모델 응답이 비어 있습니다."
    ),
    PROMPT_IMAGE_DOWNLOAD_FAILURE(
        status = ErrorStatus.INTERNAL_ERROR,
        code = "PROMPT_IMAGE_DOWNLOAD_FAILURE",
        message = "[Prompt] 이미지 다운로드에 실패했습니다."
    ),
}
