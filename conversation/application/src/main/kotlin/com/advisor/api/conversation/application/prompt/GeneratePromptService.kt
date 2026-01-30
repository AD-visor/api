package com.advisor.api.conversation.application.prompt

import com.advisor.api.ai_prompt_core.context.OutputConstraintContext
import com.advisor.api.ai_prompt_core.context.SystemContext
import com.advisor.api.ai_prompt_core.context.TaskContext
import com.advisor.api.ai_prompt_core.model.Prompt
import com.advisor.api.ai_prompt_core.model.PromptMessage
import com.advisor.api.ai_prompt_core.context.ReasoningContext
import com.advisor.api.ai_prompt_core.context.ConversationContext
import com.advisor.api.ai_prompt_core.context.DomainContext
import com.advisor.api.ai_prompt_core.context.IdentityContext
import com.advisor.api.ai_prompt_core.context.UserContext
import com.advisor.api.ai_prompt_core.model.MultimodalContent
import com.advisor.api.ai_prompt_core.model.PromptRole
import com.advisor.api.ai_prompt_core.model.PromptType
import com.advisor.api.conversation.application.prompt.scripts.IdentityScripts
import com.advisor.api.conversation.application.prompt.scripts.OutputConstraintScripts
import com.advisor.api.conversation.application.prompt.scripts.ReasoningScripts
import com.advisor.api.conversation.application.prompt.scripts.TaskScripts
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import com.advisor.api.conversation.port.inbound.command.GeneratePromptCommand
import com.advisor.api.conversation.port.inbound.prompt.GeneratePromptUseCase
import com.advisor.api.conversation.port.inbound.result.GeneratePromptResult
import org.springframework.stereotype.Service

@Service
class GeneratePromptService(
    private val systemContextProvider: SystemContextProvider,
    private val userContextProvider: UserContextProvider
) : GeneratePromptUseCase {
    override fun execute(command: GeneratePromptCommand): GeneratePromptResult {
        val conversation = command.conversation
        val messages = command.messages

        val systemContext = buildSystemContext(
            promptType = command.promptType,
            language = "KOREAN",
            toneStyle = conversation.toneStyle.value,
            speechStyle = conversation.speechStyle.value
        )

        val userContext = buildUserContext(
            businessType = conversation.businessType,
            productName = conversation.productName,
            userRequest = command.userRequest,
            messages = messages
        )

        val promptMessages = buildPrompt(
            systemContext = systemContext,
            userContext = userContext,
        )

        val prompt = Prompt(promptMessages)

        return GeneratePromptResult(prompt)
    }

    private fun buildSystemContext(
        promptType: PromptType,
        language: String,
        toneStyle: String,
        speechStyle: String
    ): SystemContext {
        val identity = IdentityContext(
            instruction = IdentityScripts.instruction(promptType),
            principles = IdentityScripts.principles(promptType),
            constraints = IdentityScripts.constraints(promptType)
        )

        val reasoning = ReasoningContext(
            guidelines = ReasoningScripts.guidelines(promptType)
        )

        val outputConstraint = OutputConstraintContext(
            language = language,
            toneStyle = toneStyle,
            speechStyle = speechStyle,
            format = OutputConstraintScripts.format(promptType),
            outputSchema = OutputConstraintScripts.schema(promptType)
        )

        return SystemContext(
            identity = identity,
            reasoning = reasoning,
            outputConstraint = outputConstraint,
        )
    }

    private fun buildUserContext(
        businessType: String,
        productName: String,
        userRequest: String,
        messages: List<ConversationMessageView>
    ): UserContext {
        val task = TaskContext(
            objective = TaskScripts.OBJECTIVE,
            description = userRequest,
            steps = TaskScripts.STEPS
        )

        val domain = DomainContext(
            businessType = businessType,
            productName = productName
        )

        val conversation = ConversationContext(
            messages = messages.map {
                val role = when (it.role) {
                    "MEMBER" -> PromptRole.USER
                    "ASSISTANT" -> PromptRole.ASSISTANT
                    else -> PromptRole.USER
                }

                PromptMessage(
                    content = listOf(MultimodalContent.Text(it.body)),
                    role = role
                )
            }
        )

        return UserContext(
            task = task,
            domain = domain,
            conversation = conversation,
        )
    }

    private fun buildPrompt(
        systemContext: SystemContext,
        userContext: UserContext?,
    ): List<PromptMessage> {
        val systemMessage = systemContextProvider.provide(systemContext)
        val userMessage = userContext?.let { userContextProvider.provide(it) } ?: emptyList()

        val allMessages = systemMessage + userMessage

        return allMessages
    }
}
