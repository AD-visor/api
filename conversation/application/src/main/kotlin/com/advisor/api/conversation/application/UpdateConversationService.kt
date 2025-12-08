package com.advisor.api.conversation.application

import com.advisor.api.common.core.domain.DomainEventPublisher
import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.conversation.domain.conversation.ConversationStore
import com.advisor.api.conversation.domain.conversation.vo.ContentPlatform
import com.advisor.api.conversation.domain.conversation.vo.SpeechStyle
import com.advisor.api.conversation.domain.conversation.vo.ToneStyle
import com.advisor.api.conversation.port.inbound.UpdateConversationUseCase
import com.advisor.api.conversation.port.inbound.command.UpdateConversationCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateConversationService(
    private val conversationStore: ConversationStore,
    private val domainEventPublisher: DomainEventPublisher
) : UpdateConversationUseCase {
    @Transactional
    override fun execute(command: UpdateConversationCommand) {
        val conversation = conversationStore.loadByIdAndMemberId(
            id = ConversationId(command.id),
            memberId = MemberId(command.memberId),
            archived = false
        )

        val updatedConversation = conversation.update(
            newBusinessType = command.businessType ?: conversation.businessType,
            newProductName = command.productName ?: conversation.productName,
            newDescription = command.description ?: conversation.description,
            newTargetAudience = command.targetAudience ?: conversation.targetAudience,
            newToneStyle = command.toneStyle?.let { ToneStyle.create(it) } ?: conversation.toneStyle,
            newSpeechStyle = command.speechStyle?.let { SpeechStyle.create(it) } ?: conversation.speechStyle,
            newContentLength = command.contentLength ?: conversation.contentLength,
            newPlatform = command.platform?.let { ContentPlatform.create(it) } ?: conversation.platform
        )

        conversationStore.save(updatedConversation)

        domainEventPublisher.publish(updatedConversation)
    }
}
