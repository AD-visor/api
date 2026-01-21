package com.advisor.api.media.adapter.outbound

import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.domain.vo.identifier.MediaId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.media.domain.Media
import com.advisor.api.media.domain.MediaProps
import com.advisor.api.media.domain.vo.MediaPath
import com.advisor.api.media.domain.vo.MediaType
import com.advisor.api.media.domain.vo.MimeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "media")
class MediaEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val path: String,

    @Column(nullable = false)
    val conversationId: Long,

    @Column(nullable = false)
    val conversationMessageId: Long,

    @Column(nullable = false)
    val memberId: Long,

    @Column(nullable = false)
    val mediaType: String,

    @Column(nullable = false)
    val mimeType: String,

    @Column(nullable = false)
    val width: Int,

    @Column(nullable = false)
    val height: Int,

    @Column(nullable = false)
    val fileSize: Long,

    @Column(nullable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val isDeleted: Boolean = false,

    @Column
    val deletedAt: Instant? = null
) {
    companion object {
        fun fromDomain(domain: Media): MediaEntity {
            return MediaEntity(
                id = domain.id.value,
                path = domain.path.value,
                conversationId = domain.conversationId.value,
                conversationMessageId = domain.conversationMessageId.value,
                memberId = domain.memberId.value,
                mediaType = domain.mediaType.value,
                mimeType = domain.mimeType.value,
                width = domain.width,
                height = domain.height,
                fileSize = domain.fileSize,
                createdAt = domain.createdAt,
                isDeleted = domain.isDeleted,
                deletedAt = domain.deletedAt
            )
        }
    }

    fun toDomain(): Media {
        val mediaProps = MediaProps(
            path = MediaPath.create(path),
            mediaType = MediaType.create(mediaType),
            mimeType = MimeType.create(mimeType),
            conversationId = ConversationId(conversationId),
            conversationMessageId = ConversationMessageId(conversationMessageId),
            memberId = MemberId(memberId),
            width = width,
            height = height,
            fileSize = fileSize,
            createdAt = createdAt,
            deletedAt = deletedAt,
            isDeleted = isDeleted
        )

        return Media.of(MediaId(id), mediaProps)
    }
}
