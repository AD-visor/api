package com.advisor.api.conversation.application.prompt

import com.advisor.api.ai_prompt_core.context.IdentityContext
import com.advisor.api.ai_prompt_core.context.OutputConstraintContext
import com.advisor.api.ai_prompt_core.context.OutputFormat
import com.advisor.api.ai_prompt_core.context.ReasoningContext
import com.advisor.api.ai_prompt_core.context.SystemContext
import com.advisor.api.ai_prompt_core.model.PromptType
import com.advisor.api.conversation.application.prompt.scripts.IdentityScripts
import com.advisor.api.conversation.application.prompt.scripts.OutputConstraintScripts
import com.advisor.api.conversation.application.prompt.scripts.ReasoningScripts
import com.advisor.api.conversation.domain.conversation.Conversation
import org.springframework.stereotype.Component

@Component
class SystemContextBuilder {
    fun build(
        promptType: PromptType,
        conversation: Conversation
    ): SystemContext {
        val identity = IdentityContext(
            instruction = IdentityScripts.instruction(promptType),
            principles = IdentityScripts.principles(promptType),
            constraints = IdentityScripts.constraints(promptType)
        )

        val reasoning = ReasoningContext(
            guidelines = ReasoningScripts.guidelines(promptType)
        )

        val outputFormat = when (promptType) {
            PromptType.COPY_WRITING -> OutputFormat.FREE_TEXT
            PromptType.INTEGRATED_DESIGN -> OutputFormat.IMAGE
            PromptType.DESIGN_CONCEPT -> OutputFormat.FREE_TEXT
        }

        val outputConstraint = OutputConstraintContext(
            language = "KOREAN",
            toneStyle = conversation.toneStyle.value,
            speechStyle = conversation.speechStyle.value,
            format = outputFormat,
            outputSchema = OutputConstraintScripts.schema(promptType)
        )

        val includeDesignExamples = when (promptType) {
            PromptType.INTEGRATED_DESIGN, PromptType.DESIGN_CONCEPT -> true
            else -> false
        }

        return SystemContext(
            identity = identity,
            reasoning = reasoning,
            outputConstraint = outputConstraint,
            includeDesignExamples = includeDesignExamples
        )
    }
}
