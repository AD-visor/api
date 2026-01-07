package com.advisor.api.conversation.application

import com.advisor.api.ai_prompt_core.model.Prompt
import com.advisor.api.common.core.domain.DomainEventPublisher
import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.infrastructure.SnowFlakeIdUtil
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.ConversationReader
import com.advisor.api.conversation.domain.conversation.ConversationStore
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessage
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import com.advisor.api.conversation.port.inbound.ProcessConversationUseCase
import com.advisor.api.conversation.port.inbound.command.GeneratePromptCommand
import com.advisor.api.conversation.port.inbound.command.ProcessConversationCommand
import com.advisor.api.conversation.port.inbound.prompt.GeneratePromptUseCase
import com.advisor.api.conversation.port.outbound.AiClientPort
import com.advisor.api.conversation.port.outbound.request.AiClientRequest
import com.advisor.api.conversation.port.outbound.response.AiClientResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProcessConversationService(
    private val conversationStore: ConversationStore,
    private val conversationReader: ConversationReader,
    private val aiClientPort: AiClientPort,
    private val generatePromptUseCase: GeneratePromptUseCase,
    private val snowFlakeIdUtil: SnowFlakeIdUtil,
    private val domainEventPublisher: DomainEventPublisher
) : ProcessConversationUseCase {

    @Transactional
    override fun execute(command: ProcessConversationCommand) {
        val conversation = conversationStore.loadByIdAndMemberId(
            id = ConversationId(command.conversationId),
            memberId = MemberId(command.memberId)
        )

        val conversationContextPairLimit = 5
        val messageLimit = conversationContextPairLimit * 2

        val messages = conversationReader.findByIdAndMemberId(
            id = command.conversationId,
            memberId = command.memberId,
            limit = messageLimit
        ).messages

        val (updatedConversation, memberMessage) = addMemberMessage(
            conversation = conversation,
            body = command.body
        )

        val aiPrompt = generateAiPrompt(
            conversation = updatedConversation,
            messages = messages,
            userRequest = memberMessage.body
        )
        val aiResponse = generateAiResponse(aiPrompt)

        val (finalConversation, aiMessage) = addAiMessage(
            conversation = updatedConversation,
            aiResponse = aiResponse.message.extractTextContent(),
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

    private fun generateAiResponse(prompt: Prompt): AiClientResponse {
        val request = AiClientRequest(
            prompt = prompt,
            maxTokens = 1000,
            temperature = 0.7f
        )

        return aiClientPort.generatePrompt(request)
    }

    private fun generateAiPrompt(
        conversation: Conversation,
        messages: List<ConversationMessageView>,
        userRequest: String
    ): Prompt {
        val generatePromptCommand = GeneratePromptCommand(
            conversation = conversation,
            messages = messages,
            userRequest = userRequest
        )

        val result = generatePromptUseCase.execute(generatePromptCommand)

        return result.prompt
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
