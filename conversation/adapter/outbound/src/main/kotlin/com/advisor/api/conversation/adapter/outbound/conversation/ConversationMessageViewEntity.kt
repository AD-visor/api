package com.advisor.api.conversation.adapter.outbound.conversation

import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.Immutable
import java.time.Instant

@Entity
@Immutable
@Table(name = "vw_conversation_message")
class ConversationMessageViewEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val conversationId: Long,

    @Column(nullable = false)
    val role: String,

    @Column(nullable = false)
    val body: String,

    @Column
    val revisionOf: Long?,

    @Column(nullable = false)
    val createdAt: Instant
) {
    fun toModel(): ConversationMessageView {
        return ConversationMessageView(
            id = id,
            conversationId = conversationId,
            role = role,
            body = body,
            revisionOf = revisionOf,
            createdAt = createdAt,
        )
    }
}
