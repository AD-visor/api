package com.advisor.api.conversation.infrastructure.conversation

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

    @Column(nullable = false, length = 2000)
    val description: String,

    @Column(nullable = false, length = 1000)
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
    fun toModel(): ConversationView {
        return ConversationView(
            id = this.id,
            memberId = this.memberId,
            businessType = this.businessType,
            productName = this.productName,
            description = this.description,
            targetAudience = this.targetAudience,
            toneStyle = this.toneStyle,
            speechStyle = this.speechStyle,
            contentLength = this.contentLength,
            platform = this.platform,
            createdAt = this.createdAt
        )
    }
}
