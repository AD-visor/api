package com.advisor.api.conversation.adapter.outbound.conversation

import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.adapter.outbound.conversation.mapper.ConversationMapper
import com.advisor.api.conversation.domain.conversation.ConversationMetadataView
import com.advisor.api.conversation.domain.conversation.ConversationReader
import com.advisor.api.conversation.domain.conversation.ConversationView
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class ConversationReaderImpl(
    private val conversationJpaReader: ConversationJpaReader,
    private val conversationMessageJpaReader: ConversationMessageJpaReader,
    private val conversationMapper: ConversationMapper,
    private val em: EntityManager
): ConversationReader {
    override fun findByIdAndMemberId(id: Long, memberId: Long): ConversationView {
        val conversationEntity = conversationJpaReader.findByIdAndMemberId(id, memberId).orElseThrow {
            CustomException(
                code = ConversationInfraExceptionCode.CONVERSATION_NOT_FOUND,
                data = "[Conversation] id=${id}, memberId=${memberId}에 해당하는 대화를 찾을 수 없습니다."
            )
        }

        val conversationMessageEntities = conversationMessageJpaReader.findAllByConversationId(id)
        val conversationMessages = conversationMessageEntities.map { it.toModel() }

        return conversationEntity.toModel(conversationMessages)
    }

    override fun findAllMetadataByMemberId(memberId: Long): List<ConversationMetadataView> {
        val projections = conversationJpaReader.findAllMetadataByMemberId(memberId)

        return projections.map { conversationMapper.toMetadataView(it) }
    }

    override fun refreshView() {
        em.createNativeQuery("REFRESH MATERIALIZED VIEW CONCURRENTLY vw_conversation")
            .executeUpdate()
    }

    override fun refreshMessageView() {
        em.createNativeQuery("REFRESH MATERIALIZED VIEW CONCURRENTLY vw_conversation_message")
            .executeUpdate()
    }
}
