package com.advisor.api.conversation.domain.conversation

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.event.AiResponseGeneratedEvent
import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessage
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageProps
import com.advisor.api.conversation.domain.conversation.event.*
import com.advisor.api.conversation.domain.conversation.vo.ContentPlatform
import com.advisor.api.conversation.domain.conversation.vo.MessageRole
import com.advisor.api.conversation.domain.conversation.vo.MessageStatus
import com.advisor.api.conversation.domain.conversation.vo.SpeechStyle
import com.advisor.api.conversation.domain.conversation.vo.ToneStyle
import java.time.Instant

class Conversation private constructor (
    id: ConversationId,
    private val props: ConversationProps
): AggregateRoot<ConversationId>(id) {
    init { validate() }

    companion object {
        fun create(id: ConversationId, props: ConversationProps): Conversation {
            val conversation = Conversation(id, props)
            conversation.addDomainEvent(ConversationCreatedEvent())

            return conversation
        }

        fun of(id: ConversationId, props: ConversationProps): Conversation {
            return Conversation(id, props)
        }
    }

    fun addMemberMessage(
        messageId: ConversationMessageId,
        body: String
    ): Pair<Conversation, ConversationMessage> {
        val (conversation, message) = addMessage(
            messageId = messageId,
            body = body,
            role = MessageRole.MEMBER
        )

        conversation.addDomainEvent(MemberMessageAddedEvent(
            conversationId = id.value,
            memberId = memberId.value,
            messageId = message.id.value
        ))

        return Pair(conversation, message)
    }

    fun completeAiMessage(
        messageId: ConversationMessageId,
        body: String,
        revisionOf: ConversationMessageId,
        parentMessageId: ConversationMessageId?
    ): Pair<Conversation, ConversationMessage> {
        val (conversation, message) = addMessage(
            messageId = messageId,
            body = body,
            role = MessageRole.ASSISTANT,
            revisionOf = revisionOf,
            parentMessageId = parentMessageId
        )

        conversation.addDomainEvent(
            AiResponseGeneratedEvent(
                conversationId = id.value,
                conversationMessageId = message.id.value,
                usedTokens = body.length / 4L,
            )
        )

        return Pair(conversation, message)
    }

    fun initiateAiMessage(
        messageId: ConversationMessageId,
        revisionOf: ConversationMessageId,
        parentMessageId: ConversationMessageId? = null
    ): Pair<Conversation, ConversationMessage> {
        val message = ConversationMessage.initiateAiMessage(
            id = messageId,
            conversationId = id,
            memberId = memberId,
            revisionOf = revisionOf,
            parentMessageId = parentMessageId
        )

        val updatedConversation = Conversation(
            id = id,
            props = props.copy(
                updatedAt = Instant.now()
            )
        )

        updatedConversation.addDomainEvent(AiMessageCreatedEvent())

        return Pair(updatedConversation, message)
    }

    fun updateMessageStatus(
        message: ConversationMessage,
        nextStatus: MessageStatus
    ): Pair<Conversation, ConversationMessage> {
        val updatedConversation = Conversation(
            id = id,
            props = props.copy(
                updatedAt = Instant.now()
            )
        )
        val updatedMessage = message.updateStatus(nextStatus)

        updatedConversation.addDomainEvent(MessageStatusUpdatedEvent())

        return Pair(updatedConversation, updatedMessage)
    }

    fun update(
        newBusinessType: String,
        newProductName: String,
        newDescription: String,
        newTargetAudience: String,
        newToneStyle: ToneStyle,
        newSpeechStyle: SpeechStyle,
        newContentLength: String,
        newPlatform: ContentPlatform
    ): Conversation {
        val updatedConversation = Conversation(id, props.copy(
            businessType = newBusinessType,
            productName = newProductName,
            description = newDescription,
            targetAudience = newTargetAudience,
            toneStyle = newToneStyle,
            speechStyle = newSpeechStyle,
            contentLength = newContentLength,
            platform = newPlatform,
            updatedAt = Instant.now()
        ))

        updatedConversation.addDomainEvent(ConversationUpdatedEvent())

        return updatedConversation
    }

    fun archive(): Conversation {
        val archivedConversation = Conversation(id, props.copy(
            isArchived = true,
            archivedAt = Instant.now(),
            updatedAt = Instant.now()
        ))

        archivedConversation.addDomainEvent(ConversationArchivedEvent())

        return archivedConversation
    }

    fun delete(): Conversation {
        addDomainEvent(ConversationDeletedEvent())

        return this
    }

    private fun addMessage(
        messageId: ConversationMessageId,
        body: String,
        role: MessageRole,
        status: MessageStatus? = null,
        revisionOf: ConversationMessageId? = null,
        parentMessageId: ConversationMessageId? = null
    ): Pair<Conversation, ConversationMessage> {
        val message = ConversationMessage.create(
            id = messageId,
            props = ConversationMessageProps(
                conversationId = id,
                memberId = memberId,
                body = body,
                role = role,
                status = status,
                revisionOf = revisionOf,
                parentMessageId = parentMessageId,
                createdAt = Instant.now()
            )
        )

        val updatedConversation = of(
            id = id,
            props = props.copy(
                updatedAt = Instant.now()
            )
        )

        return Pair(updatedConversation, message)
    }

    private fun validate() {
        require(props.businessType.isNotBlank()) { CustomException(
            ConversationDomainExceptionCode.CONVERSATION_BUSINESS_TYPE_BLANK,
            "[Conversation] 비즈니스 유형은 공백일 수 없습니다."
        ) }
        require(props.businessType.length <= 30) { CustomException(
            ConversationDomainExceptionCode.CONVERSATION_BUSINESS_TYPE_LENGTH_EXCEEDED,
            "[Conversation] 비즈니스 유형은 최대 30자 입니다."
        ) }

        require(props.productName.isNotBlank()) { CustomException(
            ConversationDomainExceptionCode.CONVERSATION_PRODUCT_NAME_BLANK,
            "[Conversation] 상품/서비스명은 공백일 수 없습니다."
        ) }
        require(props.productName.length <= 30) { CustomException(
            ConversationDomainExceptionCode.CONVERSATION_PRODUCT_NAME_LENGTH_EXCEEDED,
            "[Conversation] 상품/서비스명은 최대 30자 입니다."
        ) }

        require(props.description.length <= 1000) { CustomException(
            ConversationDomainExceptionCode.CONVERSATION_DESCRIPTION_LENGTH_EXCEEDED,
            "[Conversation] 설명은 최대 1000자 입니다."
        ) }

        require(props.targetAudience.length <= 200) { CustomException(
            ConversationDomainExceptionCode.CONVERSATION_TARGET_AUDIENCE_LENGTH_EXCEEDED,
            "[Conversation] 타겟 고객은 최대 200자 입니다."
        ) }
    }

    val memberId: MemberId get() = props.memberId
    val businessType: String get() = props.businessType
    val productName: String get() = props.productName
    val description: String get() = props.description
    val targetAudience: String get() = props.targetAudience
    val toneStyle: ToneStyle get() = props.toneStyle
    val speechStyle: SpeechStyle get() = props.speechStyle
    val contentLength: String get() = props.contentLength
    val platform: ContentPlatform get() = props.platform
    val updatedAt: Instant get() = props.updatedAt
    val createdAt: Instant get() = props.createdAt
    val isArchived: Boolean get() = props.isArchived
    val archivedAt: Instant? get() = props.archivedAt
}
