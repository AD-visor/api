package com.advisor.api.conversation.adapter.outbound.conversation

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ConversationMessageJpaStore: JpaRepository<ConversationMessageEntity, Long> {
    fun findByIdAndMemberId(id: Long, memberId: Long): Optional<ConversationMessageEntity>
}
