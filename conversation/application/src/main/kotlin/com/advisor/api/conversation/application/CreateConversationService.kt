package com.advisor.api.conversation.application

import com.advisor.api.common.core.domain.DomainEventPublisher
import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.infrastructure.SnowFlakeIdUtil
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.ConversationProps
import com.advisor.api.conversation.domain.conversation.ConversationStore
import com.advisor.api.conversation.domain.conversation.vo.ContentPlatform
import com.advisor.api.conversation.domain.conversation.vo.SpeechStyle
import com.advisor.api.conversation.domain.conversation.vo.ToneStyle
import com.advisor.api.conversation.port.inbound.CreateConversationUseCase
import com.advisor.api.conversation.port.inbound.command.CreateConversationCommand
import com.advisor.api.conversation.port.inbound.result.CreateConversationResult
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class CreateConversationService(
    private val conversationStore: ConversationStore,
    private val snowFlakeIdUtil: SnowFlakeIdUtil,
    private val domainEventPublisher: DomainEventPublisher
): CreateConversationUseCase {
    @Transactional
    override fun execute(command: CreateConversationCommand): CreateConversationResult {
        val conversationProps = ConversationProps(
            memberId = MemberId(command.memberId),
            businessType = command.businessType,
            productName = command.productName,
            description = command.description,
            targetAudience = command.targetAudience,
            toneStyle = ToneStyle.create(command.toneStyle),
            speechStyle = SpeechStyle.create(command.speechStyle),
            contentLength = command.contentLength,
            platform = ContentPlatform.create(command.platform),
            messages = emptyList(),
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        val conversation = Conversation.create(
            id = ConversationId(snowFlakeIdUtil.generateId()),
            props = conversationProps
        )

        conversationStore.save(conversation)

        domainEventPublisher.publish(conversation)

        return CreateConversationResult(conversation.id.value)
    }
}
