package com.advisor.api.conversation.adapter.outbound.conversation

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ConversationJpaStore: JpaRepository<ConversationEntity, Long> {
    fun findByIdAndMemberId(id: Long, memberId: Long): Optional<ConversationEntity>
    fun findByIdAndMemberIdAndIsArchivedFalse(id: Long, memberId: Long): Optional<ConversationEntity>
    fun findByIdAndMemberIdAndIsArchivedTrue(id: Long, memberId: Long): Optional<ConversationEntity>
}
