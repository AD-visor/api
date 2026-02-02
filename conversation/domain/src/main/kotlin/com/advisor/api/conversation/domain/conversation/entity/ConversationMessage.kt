package com.advisor.api.conversation.domain.conversation.entity

import com.advisor.api.common.core.domain.BaseDomainEntity
import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.conversation.ConversationDomainExceptionCode
import com.advisor.api.conversation.domain.conversation.vo.MessageRole
import com.advisor.api.conversation.domain.conversation.vo.MessageStatus
import java.time.Instant

class ConversationMessage private constructor(
    id: ConversationMessageId,
    private val props: ConversationMessageProps,
): BaseDomainEntity<ConversationMessageId>(id) {
    init { validate() }

    companion object {
        fun create(id: ConversationMessageId, props: ConversationMessageProps): ConversationMessage {
            return ConversationMessage(id, props)
        }

        fun of(id: ConversationMessageId, props: ConversationMessageProps): ConversationMessage {
            return ConversationMessage(id, props)
        }
    }

    fun updateStatus(nextStatus: MessageStatus): ConversationMessage {
        this.status?.canTransitionTo(nextStatus) ?: throw CustomException(
            ConversationDomainExceptionCode.CONVERSATION_INVALID_MESSAGE_STATUS,
            "[Conversation] 메시지 상태가 존재하지 않습니다."
        )

        return ConversationMessage(id, props.copy(status = nextStatus))
    }

    fun complete(finalBody: String): ConversationMessage {
        return ConversationMessage(id, props.copy(
            body = finalBody,
            status = MessageStatus.COMPLETED
        ))
    }

    private fun validate() {
        require(props.body.length <= 5000) { CustomException(
            ConversationDomainExceptionCode.CONVERSATION_MESSAGE_BODY_LENGTH_EXCEEDED,
            "[Conversation] 메시지 본문은 최대 5000자 입니다."
        ) }
    }

    val conversationId: ConversationId get() = props.conversationId
    val memberId: MemberId get() = props.memberId
    val role: MessageRole get() = props.role
    val body: String get() = props.body
    val revisionOf: ConversationMessageId? get() = props.revisionOf
    val parentMessageId: ConversationMessageId? get() = props.parentMessageId
    val status: MessageStatus? get() = props.status
    val createdAt: Instant get() = props.createdAt
}
