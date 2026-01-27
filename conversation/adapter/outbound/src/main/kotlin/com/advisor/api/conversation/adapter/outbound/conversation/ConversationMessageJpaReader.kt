package com.advisor.api.conversation.adapter.outbound.conversation

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ConversationMessageJpaReader: JpaRepository<ConversationMessageViewEntity, Long> {
    fun findAllByConversationId(conversationId: Long): List<ConversationMessageViewEntity>
    fun findAllByConversationId(conversationId: Long, pageable: Pageable): List<ConversationMessageViewEntity>
    fun findByIdAndMemberId(id: Long, memberId: Long): Optional<ConversationMessageViewEntity>
}
