package com.advisor.api.conversation.adapter.outbound.conversation

import com.advisor.api.conversation.port.inbound.view.ConversationView
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
    val platform: String,

    @Column(nullable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val updatedAt: Instant,
) {
    fun toModel(messages: List<ConversationMessageView>): ConversationView {
        return ConversationView(
            id = id,
            businessType = businessType,
            productName = productName,
            description = description,
            targetAudience = targetAudience,
            toneStyle = toneStyle,
            platform = platform,
            messages = messages,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
