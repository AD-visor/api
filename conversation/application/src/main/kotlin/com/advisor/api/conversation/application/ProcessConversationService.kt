package com.advisor.api.conversation.application

import com.advisor.api.ai_prompt_core.model.PromptType
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
import com.advisor.api.conversation.port.outbound.response.AiImageResponse
import com.advisor.api.media.port.inbound.CreateMediaUseCase
import com.advisor.api.media.port.inbound.command.CreateMediaCommand
import com.advisor.api.media.port.outbound.ImageEditPort
import com.advisor.api.media.port.outbound.command.ImageEditCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.ObjectMapper

@Service
class ProcessConversationService(
    private val conversationStore: ConversationStore,
    private val conversationReader: ConversationReader,
    private val aiClientPort: AiClientPort,
    private val generatePromptUseCase: GeneratePromptUseCase,
    private val snowFlakeIdUtil: SnowFlakeIdUtil,
    private val objectMapper: ObjectMapper,
    private val createMediaUseCase: CreateMediaUseCase,
    private val imageEditPort: ImageEditPort,
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

        val copyWrite = executeAiTextStep(conversation, messages, command.body, PromptType.COPY_WRITING)

        val imageStepInput = "Based on this copy: '${copyWrite}', generate a background image prompt."
        val imageGeneration = executeAiImageStep(conversation, messages, imageStepInput, PromptType.IMAGE_GENERATION)

        val layoutStepInput = """
            Copy: $copyWrite
            Image Concept: $imageGeneration
            Analyze the visual hierarchy.
            Provide a JSON ARRAY of coordinates for each text element (Main copy, Sub copy, etc).
        """.trimIndent()
        val layoutAnalysis = executeAiTextStep(conversation, messages, layoutStepInput, PromptType.LAYOUT_ANALYSIS)
        val textElements: List<ImageEditCommand.TextElement> = objectMapper.readValue(
            layoutAnalysis,
            object : TypeReference<List<ImageEditCommand.TextElement>>() {}
        )

        val compositeCommand = ImageEditCommand.Composite(
            baseImage = imageGeneration.bytes,
            textElements = textElements
        )

        // 3. 최종 이미지 파일 저장
        val finalImageBytes = imageEditPort.composite(compositeCommand).image

        val createMediaCommand = CreateMediaCommand(
            conversationId = command.conversationId,
            conversationMessageId = memberMessage.id.value,
            memberId = command.memberId,
            file = finalImageBytes,
            mimeType = "image/png",
            width = 1024,
            height = 1024
        )
        createMediaUseCase.execute(listOf(createMediaCommand))

        val (finalConversation, aiMessage) = addAiMessage(
            conversation = updatedConversation,
            aiResponse = copyWrite,
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

    private fun executeAiTextStep(
        conversation: Conversation,
        messages: List<ConversationMessageView>,
        requestBody: String,
        type: PromptType
    ): String {
        val promptCommand = GeneratePromptCommand(
            conversation = conversation,
            messages = messages,
            userRequest = requestBody,
            promptType = type
        )

        val promptResult = generatePromptUseCase.execute(promptCommand)
        val response = aiClientPort.generatePrompt(AiClientRequest(promptResult.prompt))

        return response.message.extractTextContent()
    }

    private fun executeAiImageStep(
        conversation: Conversation,
        messages: List<ConversationMessageView>,
        requestBody: String,
        type: PromptType
    ): AiImageResponse {
        val promptCommand = GeneratePromptCommand(
            conversation = conversation,
            messages = messages,
            userRequest = requestBody,
            promptType = type
        )

        val promptResult = generatePromptUseCase.execute(promptCommand)
        val response = aiClientPort.generateImage(AiClientRequest(promptResult.prompt))

        return response
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
