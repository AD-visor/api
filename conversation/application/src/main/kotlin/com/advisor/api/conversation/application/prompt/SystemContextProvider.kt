package com.advisor.api.conversation.application.prompt

import com.advisor.api.ai_prompt_core.context.ChainOfThoughtPolicy
import com.advisor.api.ai_prompt_core.context.IdentityContext
import com.advisor.api.ai_prompt_core.context.OutputConstraintContext
import com.advisor.api.ai_prompt_core.context.OutputFormat
import com.advisor.api.ai_prompt_core.context.ReasoningContext
import com.advisor.api.ai_prompt_core.context.SystemContext
import com.advisor.api.ai_prompt_core.model.PromptMessage
import com.advisor.api.ai_prompt_core.phase.PromptPhase
import com.advisor.api.ai_prompt_core.phase.PromptPhaseProvider
import com.advisor.api.conversation.application.prompt.scripts.OutputConstraintScripts
import com.advisor.api.conversation.application.prompt.scripts.ReasoningScripts
import org.springframework.stereotype.Component

@Component
class SystemContextProvider : PromptPhaseProvider<SystemContext> {
    override val phase: PromptPhase = PromptPhase.SYSTEM_CONTEXT

    override fun provide(context: SystemContext): List<PromptMessage> {
        val systemMessage = buildString {
            // 1. Identity
            appendLine("\n---\n")
            appendLine("# IDENTITY CONTEXT")
            appendLine(buildIdentityMessage(context.identity))

            // 2. Reasoning
            appendLine("\n---\n")
            appendLine("# REASONING CONTEXT")
            appendLine(buildReasoningMessage(context.reasoning))

            // 3. Output Constraint
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

            appendLine("\n## ADDITIONAL CONSTRAINTS")
            identity.constraints.forEach { appendLine("- $it") }
        }.trim()
    }

    private fun buildReasoningMessage(reasoning: ReasoningContext): String {
        return buildString {
            // 1. Chain of Thought 지시
            when (reasoning.chainOfThought) {
                ChainOfThoughtPolicy.INTERNAL -> {
                    appendLine("## Reasoning Guideline")
                    appendLine(ReasoningScripts.COT_INTERNAL)
                }
                ChainOfThoughtPolicy.SELF_CHECK -> {
                    appendLine("## Self-Correction Guideline")
                    appendLine(ReasoningScripts.COT_SELF_CHECK)
                }
                ChainOfThoughtPolicy.NONE -> {  }
            }
        }.trim()
    }

    private fun buildOutputConstraintMessage(constraint: OutputConstraintContext): String {
        return buildString {
            appendLine("## OUTPUT SPECIFICATIONS")
            appendLine("- ${OutputConstraintScripts.language(constraint.language)}")
            appendLine("- ${OutputConstraintScripts.tone(constraint.toneStyle)}")
            appendLine("- ${OutputConstraintScripts.speech(constraint.speechStyle)}")

            if (constraint.format != OutputFormat.FREE_TEXT) {
                appendLine("- Format: ${constraint.format}")
            }

            constraint.schemaDescription?.let {
                appendLine("## Target Schema")
                appendLine("```json\n$it\n```")
            }
        }.trim()
    }
}
