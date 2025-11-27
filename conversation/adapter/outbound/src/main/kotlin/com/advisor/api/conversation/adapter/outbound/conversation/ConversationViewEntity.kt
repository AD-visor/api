package com.advisor.api.conversation.adapter.outbound.conversation

import com.advisor.api.conversation.domain.conversation.ConversationView
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.Immutable
import java.time.Instant

@Entity
@Immutable
@Table(name = "vw_conversation_message")
class ConversationViewEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val memberId: Long,

    @Column(nullable = false)
    val businessType: String,

    @Column(nullable = false)
    val productName: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val targetAudience: String,

    @Column(nullable = false)
    val toneStyle: String,

    @Column(nullable = false)
    val speechStyle: String,

    @Column(nullable = false)
    val contentLength: String,

    @Column(nullable = false)
    val platform: String,

    @Column(nullable = false)
    val createdAt: Instant
) {
    companion object {
        fun toModel(entity: ConversationViewEntity): ConversationView {
            return ConversationView(
                id = entity.id,
                memberId = entity.memberId,
                businessType = entity.businessType,
                productName = entity.productName,
                description = entity.description,
                targetAudience = entity.targetAudience,
                toneStyle = entity.toneStyle,
                speechStyle = entity.speechStyle,
                contentLength = entity.contentLength,
                platform = entity.platform,
                createdAt = entity.createdAt
            )
        }
    }
}
