package com.advisor.api.conversation.adapter.outbound.conversation

import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.conversation.ConversationReader
import com.advisor.api.conversation.domain.conversation.ConversationView
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class ConversationReaderImpl(
    private val conversationJpaReader: ConversationJpaReader,
    private val conversationMessageJpaReader: ConversationMessageJpaReader,
    private val em: EntityManager
): ConversationReader {
    override fun refreshView() {
        em.createNativeQuery("REFRESH MATERIALIZED VIEW CONCURRENTLY vw_conversation")
            .executeUpdate()
    }

    override fun refreshMessageView() {
        em.createNativeQuery("REFRESH MATERIALIZED VIEW CONCURRENTLY vw_conversation_message")
            .executeUpdate()
    }
}
