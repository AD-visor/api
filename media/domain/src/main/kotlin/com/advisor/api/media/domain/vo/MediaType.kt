package com.advisor.api.media.domain.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.media.domain.MediaDomainExceptionCode

class MediaType private constructor(val value: String) {
    init { validate() }

    companion object {
        const val IMAGE = "IMAGE"
        const val VIDEO = "VIDEO"
        const val DOCUMENT = "DOCUMENT"

        val allowedMediaTypes = setOf(IMAGE, VIDEO, DOCUMENT)

        fun create(value: String): MediaType {
            return MediaType(value)
        }
    }

    private fun validate() {
        require(value in allowedMediaTypes) { CustomException(
            MediaDomainExceptionCode.MEDIA_INVALID_MEDIA_TYPE,
            "[Media] 유효하지 않은 미디어 타입입니다."
        ) }
    }

    override fun equals(other: Any?): Boolean = other is MediaType && this.value == other.value
    override fun hashCode(): Int = value.hashCode()
}
