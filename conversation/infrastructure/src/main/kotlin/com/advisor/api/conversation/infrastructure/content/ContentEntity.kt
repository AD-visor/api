package com.advisor.api.conversation.infrastructure.content

import com.advisor.api.common.core.domain.vo.identifier.ContentId
import com.advisor.api.common.core.domain.vo.identifier.ContentRequestId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.conversation.domain.content.Content
import com.advisor.api.conversation.domain.content.ContentProps
import com.advisor.api.conversation.domain.content.vo.ContentPlatform
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "content")
class ContentEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val memberId: Long,

    @Column(nullable = false)
    val contentRequestId: Long,

    @Column(nullable = false, length = 100)
    val title: String,

    @Column(nullable = false, length = 5000)
    val body: String,

    @Column(nullable = false)
    val platform: String,

    @Column(nullable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val updatedAt: Instant,

    @Column(nullable = false)
    val isDeleted: Boolean,

    @Column
    val deletedAt: Instant?
) {
    companion object {
        fun fromDomain(domain: Content): ContentEntity {
            return ContentEntity(
                id = domain.id.value,
                memberId = domain.memberId.value,
                contentRequestId = domain.contentRequestId.value,
                title = domain.title,
                body = domain.body,
                platform = domain.platform.value,
                createdAt = domain.createdAt,
                updatedAt = domain.updatedAt,
                isDeleted = domain.isDeleted,
                deletedAt = domain.deletedAt
            )
        }
    }

    fun toDomain(): Content {
        val contentProps = ContentProps(
            memberId = MemberId(memberId),
            contentRequestId = ContentRequestId(contentRequestId),
            title = title,
            body = body,
            platform = ContentPlatform.create(platform),
            createdAt = createdAt,
            updatedAt = updatedAt,
            isDeleted = isDeleted,
            deletedAt = deletedAt
        )

        return Content.of(ContentId(id), contentProps)
    }
}
