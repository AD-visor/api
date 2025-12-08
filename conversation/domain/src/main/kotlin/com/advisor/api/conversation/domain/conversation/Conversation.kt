package com.advisor.api.conversation.domain.conversation

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessage
import com.advisor.api.conversation.domain.conversation.event.ConversationArchivedEvent
import com.advisor.api.conversation.domain.conversation.event.ConversationCreatedEvent
import com.advisor.api.conversation.domain.conversation.event.ConversationDeletedEvent
import com.advisor.api.conversation.domain.conversation.event.ConversationUpdatedEvent
import com.advisor.api.conversation.domain.conversation.vo.ContentPlatform
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
    val messages: List<ConversationMessage> get() = props.messages
    val createdAt: Instant get() = props.createdAt
    val isArchived: Boolean get() = props.isArchived
    val archivedAt: Instant? get() = props.archivedAt
}
