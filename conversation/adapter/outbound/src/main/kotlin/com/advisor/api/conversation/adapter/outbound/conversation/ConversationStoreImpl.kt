package com.advisor.api.conversation.adapter.outbound.conversation

import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.ConversationStore
import org.springframework.stereotype.Repository

@Repository
class ConversationStoreImpl(
    private val jpaStore: ConversationJpaStore,
    private val messageJpaStore: ConversationMessageJpaStore
): ConversationStore {
    override fun save(conversation: Conversation) {
        val entity = ConversationEntity.fromDomain(conversation)
        jpaStore.save(entity)

        conversation.messages.forEach {
            val messageEntity = ConversationMessageEntity.fromDomain(it, conversation.id)
            messageJpaStore.save(messageEntity)
        }
    }

    override fun loadById(id: ConversationId): Conversation {
        val entity = jpaStore.findById(id.value).orElseThrow { CustomException(
            ConversationInfraExceptionCode.CONVERSATION_NOT_FOUND,
            "[Conversation] id=${id.value}에 해당하는 대화를 찾을 수 없습니다."
        ) }

        return entity.toDomain()
    }
}
