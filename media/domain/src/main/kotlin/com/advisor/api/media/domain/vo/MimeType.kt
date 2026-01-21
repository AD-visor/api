package com.advisor.api.media.domain.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.media.domain.MediaDomainExceptionCode

class MimeType private constructor(val value: String) {
    init { validate() }

    companion object {
        fun create(value: String): MimeType {
            return MimeType(value)
        }
    }

    val mainType: String get() = value.split("/")[0]
    fun isImage(): Boolean = mainType == "image"
    fun isVideo(): Boolean = mainType == "video"

    private fun validate() {
        require(isImage() || isVideo()) { CustomException(
            MediaDomainExceptionCode.MEDIA_INVALID_MIME_TYPE,
            "[Media] 유효하지 않은 MIME 타입입니다."
        ) }
    }
}
