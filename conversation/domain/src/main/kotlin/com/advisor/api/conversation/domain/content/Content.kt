package com.advisor.api.conversation.domain.content

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.vo.identifier.ContentId
import com.advisor.api.common.core.domain.vo.identifier.ContentRequestId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.content.vo.ContentPlatform
import java.time.Instant

class Content private constructor(
    id: ContentId,
    private val props: ContentProps
): AggregateRoot<ContentId>(id) {
    init { validate() }

    companion object {
        fun create(id: ContentId, props: ContentProps): Content {
            return Content(id, props)
        }

        fun of(id: ContentId, props: ContentProps): Content {
            return Content(id, props)
        }
    }

    private fun validate() {
        require(props.title.isNotBlank()) { CustomException(
            ContentDomainExceptionCode.CONTENT_TITLE_BLANK,
            "[Content] 제목은 공백일 수 없습니다."
        ) }

        require(props.title.length <= 100) { CustomException(
            ContentDomainExceptionCode.CONTENT_TITLE_LENGTH_EXCEEDED,
            "[Content] 제목은 최대 100자 입니다."
        ) }

        require(props.body.length <= 5000) { CustomException(
            ContentDomainExceptionCode.CONTENT_BODY_LENGTH_EXCEEDED,
            "[Content] 본문은 최대 5000자 입니다."
        ) }
    }

    val memberId: MemberId get() = props.memberId
    val contentRequestId: ContentRequestId get() = props.contentRequestId
    val title: String get() = props.title
    val body: String get() = props.body
    val platform: ContentPlatform get() = props.platform
    val createdAt: Instant get() = props.createdAt
    val updatedAt: Instant get() = props.updatedAt
    val isDeleted: Boolean get() = props.isDeleted
    val deletedAt: Instant? get() = props.deletedAt
}
