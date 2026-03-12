package com.advisor.api.conversation.application.prompt

import com.advisor.api.ai_prompt_core.context.IdentityContext
import com.advisor.api.ai_prompt_core.context.OutputConstraintContext
import com.advisor.api.ai_prompt_core.context.OutputFormat
import com.advisor.api.ai_prompt_core.context.ReasoningContext
import com.advisor.api.ai_prompt_core.context.SystemContext
import com.advisor.api.ai_prompt_core.model.PromptMessage
import com.advisor.api.ai_prompt_core.phase.PromptPhase
import com.advisor.api.ai_prompt_core.phase.PromptPhaseProvider
import com.advisor.api.conversation.application.prompt.scripts.OutputConstraintScripts
import com.advisor.api.conversation.application.prompt.scripts.identity.IntegratedDesignIdentityScripts
import org.springframework.stereotype.Component

@Component
class SystemContextProvider : PromptPhaseProvider<SystemContext> {
    override val phase: PromptPhase = PromptPhase.SYSTEM_CONTEXT

    override fun provide(context: SystemContext): List<PromptMessage> {
        val systemMessage = buildString {
            // 1. Identity
            appendLine("# IDENTITY CONTEXT")
            appendLine(buildIdentityMessage(context.identity))

            // 2. Reasoning
            appendLine("\n---\n")
            appendLine("# REASONING CONTEXT")
            appendLine(buildReasoningMessage(context.reasoning))

            // 3. Design Style Examples (INTEGRATED_DESIGN일 때만)
            if (context.includeDesignExamples) {
                appendLine("\n---\n")
                appendLine("# DESIGN STYLE REFERENCE")
                appendLine(IntegratedDesignIdentityScripts.STYLE_EXAMPLES)
            }

            // 4. Output Constraint
            appendLine("\n---\n")
            appendLine("# OUTPUT CONSTRAINT CONTEXT")
            appendLine(buildOutputConstraintMessage(context.outputConstraint))
        }.trim()

        return listOf(PromptMessage.system(systemMessage))
    }

    private fun buildIdentityMessage(identity: IdentityContext): String {
        return buildString {
            appendLine("## ROLE")
            appendLine(identity.instruction)

            appendLine("\n## CORE PRINCIPLES")
            identity.principles.forEach { appendLine("- $it") }

            appendLine("\n## CONSTRAINTS")
            identity.constraints.forEach { appendLine("- $it") }
        }.trim()
    }

    private fun buildReasoningMessage(reasoning: ReasoningContext): String {
        return buildString {
            appendLine("## REASONING GUIDELINES")
            reasoning.guidelines.forEach {
                appendLine(it)
                appendLine()
            }
        }.trim()
    }

    private fun buildOutputConstraintMessage(constraint: OutputConstraintContext): String {
        return buildString {
            appendLine("## OUTPUT SPECIFICATIONS")
            appendLine("- ${OutputConstraintScripts.language(constraint.language)}")
            appendLine("- ${OutputConstraintScripts.tone(constraint.toneStyle)}")
            appendLine("- ${OutputConstraintScripts.speech(constraint.speechStyle)}")

            if (constraint.format == OutputFormat.IMAGE) {
                appendLine("- ${OutputConstraintScripts.imageGenerationFormat()}")
                appendLine("\n${OutputConstraintScripts.designCompleteness()}")
            }

            if (constraint.format == OutputFormat.JSON) {
                appendLine("- Format: ${constraint.format}")
            }

            if (constraint.outputSchema != null) {
                appendLine("\n## OUTPUT SCHEMA")
                appendLine(constraint.outputSchema)
            }
        }.trim()
    }
}
