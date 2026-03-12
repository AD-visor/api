package com.advisor.api.conversation.application.prompt

import com.advisor.api.ai_prompt_core.context.SystemContext
import com.advisor.api.ai_prompt_core.model.Prompt
import com.advisor.api.ai_prompt_core.model.PromptMessage
import com.advisor.api.ai_prompt_core.context.UserContext
import com.advisor.api.conversation.port.inbound.command.GeneratePromptCommand
import com.advisor.api.conversation.port.inbound.prompt.GeneratePromptUseCase
import com.advisor.api.conversation.port.inbound.result.GeneratePromptResult
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class GeneratePromptService(
    private val systemContextProvider: SystemContextProvider,
    private val userContextProvider: UserContextProvider,
    private val systemContextBuilder: SystemContextBuilder,
    private val userContextBuilder: UserContextBuilder
) : GeneratePromptUseCase {

    private val logger = KotlinLogging.logger {}

    override fun execute(command: GeneratePromptCommand): GeneratePromptResult {
        logger.info { "프롬프트 생성: type=${command.promptType}" }

        val systemContext = systemContextBuilder.build(command.promptType, command.conversation)
        val userContext = userContextBuilder.build(command)
        val promptMessages = buildPrompt(systemContext, userContext)

        logger.info { "프롬프트 생성 완료" }

        return GeneratePromptResult(Prompt(promptMessages))
    }

    private fun buildPrompt(
        systemContext: SystemContext,
        userContext: UserContext
    ): List<PromptMessage> {
        val systemMessage = systemContextProvider.provide(systemContext)
        val userMessage = userContextProvider.provide(userContext)
        return systemMessage + userMessage
    }
}
