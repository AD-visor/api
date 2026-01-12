package com.advisor.api.media.domain.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.media.domain.MediaDomainExceptionCode

class MediaPath private constructor(val value: String) {
    init { validate() }

    companion object {
        fun create(value: String): MediaPath {
            return MediaPath(value)
        }
    }

    private fun validate() {
        require(value.isNotBlank()) { CustomException(
            MediaDomainExceptionCode.MEDIA_PATH_BLANK,
            "[Media] 미디어 경로는 비어 있을 수 없습니다."
        ) }
    }

    override fun equals(other: Any?): Boolean = other is MediaPath && this.value == other.value
    override fun hashCode(): Int = value.hashCode()
}
