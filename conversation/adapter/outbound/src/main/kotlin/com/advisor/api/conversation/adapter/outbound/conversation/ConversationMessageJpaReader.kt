package com.advisor.api.conversation.adapter.outbound.conversation

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ConversationMessageJpaReader: JpaRepository<ConversationMessageViewEntity, Long> {
    fun findAllByConversationId(conversationMessageId: Long): List<ConversationMessageViewEntity>
    fun findAllByConversationId(conversationId: Long, pageable: Pageable): List<ConversationMessageViewEntity>
}
