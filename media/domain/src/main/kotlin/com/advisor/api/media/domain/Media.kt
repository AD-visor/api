package com.advisor.api.media.domain

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.vo.identifier.MediaId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.media.domain.vo.MediaPath
import com.advisor.api.media.domain.vo.MediaType
import com.advisor.api.media.domain.vo.MimeType
import java.time.Instant

class Media private constructor(
    id: MediaId,
    private val props: MediaProps
): AggregateRoot<MediaId>(id) {
    init { validate() }

    companion object {
        fun create(id: MediaId, props: MediaProps): Media {
            return Media(id, props)
        }

        fun of(id: MediaId, props: MediaProps): Media {
            return Media(id, props)
        }
    }

    private fun validate() {
        require(fileSize >= 0 && fileSize <= 5 * 1024 * 1024) { CustomException(
            MediaDomainExceptionCode.MEDIA_FILE_SIZE_EXCEEDED,
            "[Media] 파일 크기는 0에서 5MB 사이여야 합니다."
        ) }
    }

    val path: MediaPath get() = props.path
    val mediaType: MediaType get() = props.mediaType
    val mimeType: MimeType get() = props.mimeType
    val conversationId get() = props.conversationId
    val conversationMessageId get() = props.conversationMessageId
    val width: Int get() = props.width
    val height: Int get() = props.height
    val fileSize: Long get() = props.fileSize
    val createdAt: Instant get() = props.createdAt
    val isDeleted: Boolean get() = props.isDeleted
    val deletedAt: Instant? get() = props.deletedAt
}
