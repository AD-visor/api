package com.advisor.api.conversation.adapter.outbound.conversation

import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.adapter.outbound.conversation.mapper.ConversationMapper
import com.advisor.api.conversation.domain.conversation.ConversationMetadataView
import com.advisor.api.conversation.domain.conversation.ConversationReader
import com.advisor.api.conversation.domain.conversation.ConversationView
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import jakarta.persistence.EntityManager
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository

@Repository
class ConversationReaderImpl(
    private val conversationJpaReader: ConversationJpaReader,
    private val conversationMessageJpaReader: ConversationMessageJpaReader,
    private val conversationMapper: ConversationMapper,
    private val em: EntityManager
): ConversationReader {
    override fun findByIdAndMemberId(id: Long, memberId: Long, limit: Int?): ConversationView {
        val conversationEntity = conversationJpaReader.findByIdAndMemberId(id, memberId).orElseThrow {
            CustomException(
                code = ConversationInfraExceptionCode.CONVERSATION_NOT_FOUND,
                data = "[Conversation] id=${id}, memberId=${memberId}에 해당하는 대화를 찾을 수 없습니다."
            )
        }

        val conversationMessageEntities = if (limit != null && limit > 0) {
            val pageable = PageRequest.of(
                0,
                limit,
                Sort.by(
                    Sort.Order.desc("createdAt"),
                    Sort.Order.desc("id")
                )
            )
            conversationMessageJpaReader.findAllByConversationId(id, pageable)
                .reversed()
        } else {
            conversationMessageJpaReader.findAllByConversationId(id)
        }

        val conversationMessages = conversationMessageEntities.map { it.toModel() }

        return conversationEntity.toModel(conversationMessages)
    }

    override fun findAllMetadataByMemberId(memberId: Long): List<ConversationMetadataView> {
        val projections = conversationJpaReader.findAllMetadataByMemberId(memberId)

        return projections.map { conversationMapper.toMetadataView(it) }
    }

    override fun findPairByAiMessageIdAndConversationIdAndMemberId(
        aiMessageId: Long,
        conversationId: Long,
        memberId: Long
    ): List<ConversationMessageView> {
        val aiMessage = conversationMessageJpaReader.findByIdAndConversationIdAndMemberId(
            aiMessageId,
            conversationId,
            memberId
        ).orElseThrow {
            CustomException(
                code = ConversationInfraExceptionCode.CONVERSATION_MESSAGE_NOT_FOUND,
                data = "[ConversationMessage] id=${aiMessageId}에 해당하는 대화 메시지를 찾을 수 없습니다."
            )
        }

        val parentId = aiMessage.parentMessageId

        return if (parentId != null) {
            val memberMessage = conversationMessageJpaReader.findByIdAndConversationIdAndMemberId(
                parentId,
                conversationId,
                memberId
            ).orElseThrow {
                CustomException(
                    code = ConversationInfraExceptionCode.CONVERSATION_MESSAGE_NOT_FOUND,
                    data = "[ConversationMessage] 부모 메시지 id=$parentId 를 찾을 수 없습니다."
                )
            }
            listOf(memberMessage.toModel(), aiMessage.toModel())
        } else {
            listOf(aiMessage.toModel())
        }
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
