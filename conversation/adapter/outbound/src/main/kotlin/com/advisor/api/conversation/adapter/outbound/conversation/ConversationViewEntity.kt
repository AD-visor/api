package com.advisor.api.conversation.adapter.outbound.conversation

import com.advisor.api.conversation.domain.conversation.ConversationView
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import jakarta.persistence.*
import org.springframework.data.annotation.Immutable
import java.time.Instant

@Entity
@Immutable
@Table(name = "vw_conversation")
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
    fun toModel(messages: List<ConversationMessageView>): ConversationView {
        return ConversationView(
            id = id,
            memberId = memberId,
            businessType = businessType,
            productName = productName,
            description = description,
            targetAudience = targetAudience,
            toneStyle = toneStyle,
            speechStyle = speechStyle,
            contentLength = contentLength,
            platform = platform,
            messages = messages,
            createdAt = createdAt
        )
    }
}
