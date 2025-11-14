package com.advisor.api.conversation.infrastructure.conversation

import org.springframework.data.jpa.repository.JpaRepository

interface ConversationJpaStore: JpaRepository<ConversationEntity, Long>
