package com.advisor.api.conversation.application

import com.advisor.api.common.core.domain.DomainEventPublisher
import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.infrastructure.SnowFlakeIdUtil
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.ConversationStore
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessage
import com.advisor.api.conversation.port.inbound.ProcessConversationUseCase
import com.advisor.api.conversation.port.inbound.command.ProcessConversationCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProcessConversationService(
    private val conversationStore: ConversationStore,
    private val snowFlakeIdUtil: SnowFlakeIdUtil,
    private val domainEventPublisher: DomainEventPublisher
) : ProcessConversationUseCase {
    @Transactional
    override fun execute(command: ProcessConversationCommand) {
        val conversation = conversationStore.loadByIdAndMemberId(
            id = ConversationId(command.conversationId),
            memberId = MemberId(command.memberId)
        )

        val (updatedConversation, memberMessage) = addMemberMessage(
            conversation = conversation,
            body = command.body
        )

        val aiResponse = generateAiResponse()

        val (finalConversation, aiMessage) = addAiMessage(
            conversation = updatedConversation,
            aiResponse = aiResponse,
            revisionOf = memberMessage.id
        )

        domainEventPublisher.publish(finalConversation)
    }

    private fun addMemberMessage(
        conversation: Conversation,
        body: String
    ): Pair<Conversation, ConversationMessage> {
        val (updatedConversation, message) = conversation.addMemberMessage(
            messageId = ConversationMessageId(snowFlakeIdUtil.generateId()),
            body = body
        )

        conversationStore.save(updatedConversation)
        conversationStore.saveNewMessage(message)

        return Pair(updatedConversation, message)
    }

    private fun generateAiResponse(): String {
        return "실제 AI 응답 생성 로직 구현 예정"
    }

    private fun addAiMessage(
        conversation: Conversation,
        aiResponse: String,
        revisionOf: ConversationMessageId
    ): Pair<Conversation, ConversationMessage> {
        val (updatedConversation, message) = conversation.addAiMessage(
            messageId = ConversationMessageId(snowFlakeIdUtil.generateId()),
            body = aiResponse,
            revisionOf = revisionOf
        )

        conversationStore.save(updatedConversation)
        conversationStore.saveNewMessage(message)

        return Pair(updatedConversation, message)
    }
}
