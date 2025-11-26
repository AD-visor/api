package com.advisor.api.conversation.adapter.outbound.conversation

import org.springframework.data.jpa.repository.JpaRepository

interface ConversationMessageJpaStore: JpaRepository<ConversationMessageEntity, Long>
