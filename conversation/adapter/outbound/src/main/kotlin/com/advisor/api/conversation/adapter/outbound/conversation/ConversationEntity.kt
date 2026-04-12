package com.advisor.api.conversation.adapter.outbound.conversation

import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.ConversationProps
import com.advisor.api.conversation.domain.conversation.vo.ContentPlatform
import com.advisor.api.conversation.domain.conversation.vo.ToneStyle
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(
    name = "conversation",
    indexes = [
        Index(name = "idx_conversation_conversation_member", columnList = "id, member_id"),
    ]
)
class ConversationEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val memberId: Long,

    @Column(nullable = false, length = 30)
    val businessType: String,

    @Column(nullable = false, length = 30)
    val productName: String,

    @Column(nullable = false, length = 1000)
    val description: String,

    @Column(nullable = false, length = 200)
    val targetAudience: String,

    @Column(nullable = false)
    val toneStyle: String,

    @Column(nullable = false)
    val platform: String,

    @Column(nullable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val updatedAt: Instant,

    @Column(nullable = false)
    val isArchived: Boolean = false,

    @Column
    val archivedAt: Instant? = null,
) {
    companion object {
        fun fromDomain(domain: Conversation): ConversationEntity {
            return ConversationEntity(
                id = domain.id.value,
                memberId = domain.memberId.value,
                businessType = domain.businessType,
                productName = domain.productName,
                description = domain.description,
                targetAudience = domain.targetAudience,
                toneStyle = domain.toneStyle.value,
                platform = domain.platform.value,
                createdAt = domain.createdAt,
                updatedAt = domain.updatedAt,
                isArchived = domain.isArchived,
                archivedAt = domain.archivedAt,
            )
        }
    }

    fun toDomain(): Conversation {
        val props = ConversationProps(
            memberId = MemberId(memberId),
            businessType = businessType,
            productName = productName,
            description = description,
            targetAudience = targetAudience,
            toneStyle = ToneStyle.create(toneStyle),
            platform = ContentPlatform.create(platform),
            createdAt = createdAt,
            updatedAt = updatedAt,
            isArchived = isArchived,
            archivedAt = archivedAt,
        )

        return Conversation.of(ConversationId(id), props)
    }
}
