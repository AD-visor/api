package com.advisor.api.conversation.adapter.outbound.conversation

import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessage
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageProps
import com.advisor.api.conversation.domain.conversation.vo.MessageRole
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(
    name = "conversation_message", indexes = [
        Index(name = "idx_message_conversation_id_created_at", columnList = "conversation_id, created_at")
    ]
)
class ConversationMessageEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val conversationId: Long,

    @Column(nullable = false)
    val role: String,

    @Column(nullable = false, length = 5000)
    val body: String,

    @Column(nullable = true)
    val revisionOf: Long? = null,

    @Column(nullable = true)
    val parentMessageId: Long? = null,

    @Column(nullable = false)
    val createdAt: Instant
) {
    companion object {
        fun fromDomain(domain: ConversationMessage): ConversationMessageEntity {
            return ConversationMessageEntity(
                id = domain.id.value,
                conversationId = domain.conversationId.value,
                role = domain.role.value,
                body = domain.body,
                revisionOf = domain.revisionOf?.value,
                parentMessageId = domain.parentMessageId?.value,
                createdAt = domain.createdAt
            )
        }
    }

    fun toDomain(): ConversationMessage {
        val props = ConversationMessageProps(
            conversationId = ConversationId(conversationId),
            role = MessageRole.create(role),
            body = body,
            revisionOf = revisionOf?.let { ConversationMessageId(it) },
            parentMessageId = parentMessageId?.let { ConversationMessageId(it) },
            createdAt = createdAt
        )

        return ConversationMessage.of(ConversationMessageId(id), props)
    }
}
