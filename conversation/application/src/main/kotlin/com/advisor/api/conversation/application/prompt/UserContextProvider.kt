package com.advisor.api.conversation.application.prompt

import com.advisor.api.ai_prompt_core.context.DesignBriefContext
import com.advisor.api.ai_prompt_core.context.DomainContext
import com.advisor.api.ai_prompt_core.context.TaskContext
import com.advisor.api.ai_prompt_core.context.UserContext
import com.advisor.api.ai_prompt_core.model.PromptMessage
import com.advisor.api.ai_prompt_core.phase.PromptPhase
import com.advisor.api.ai_prompt_core.phase.PromptPhaseProvider
import com.advisor.api.conversation.application.prompt.scripts.TaskScripts
import com.advisor.api.conversation.application.prompt.scripts.identity.CopyWritingIdentityScripts
import org.springframework.stereotype.Component

@Component
class UserContextProvider : PromptPhaseProvider<UserContext> {
    override val phase = PromptPhase.USER_CONTEXT

    override fun provide(context: UserContext): List<PromptMessage> {
        val messages = mutableListOf<PromptMessage>()

        context.conversation?.let { conversation ->
            if (conversation.messages.isNotEmpty()) {
                val conversationText = buildString {
                    appendLine("# CONVERSATION HISTORY")
                    appendLine("The following is the history of the conversation so far:")
                }.trim()

                messages.add(PromptMessage.user(conversationText))
                conversation.messages.forEach { messages.add(it) }
                messages.add(PromptMessage.user("\n---\n"))
            }
        }

        val coreText = buildString {
            context.task?.let {
                appendLine("# TASK")
                appendLine(buildTaskMessage(it))
            }

            context.domain?.let {
                appendLine("\n---\n")
                appendLine("# DOMAIN CONTEXT")
                appendLine(buildDomainMessage(it))
            }

            context.designBrief?.let {
                appendLine("\n---\n")
                appendLine("# DESIGN BRIEF")
                appendLine(buildDesignBriefMessage(it))
            }
        }.trim()

        messages.add(PromptMessage.user(coreText))

        return messages
    }

    private fun buildTaskMessage(task: TaskContext): String {
        return buildString {
            appendLine("## 1. MISSION OBJECTIVE")
            appendLine(task.objective)

            appendLine("\n## 2. TASK DESCRIPTION")
            appendLine(task.description)

            if (task.requirements.isNotEmpty()) {
                appendLine("\n## 3. KEY REQUIREMENTS")
                task.requirements.forEach { appendLine("- $it") }
            }

            val finalSteps = task.steps.ifEmpty { TaskScripts.INTEGRATED_DESIGN_STEPS }
            appendLine("\n## 4. EXECUTION STEPS")
            finalSteps.forEach { appendLine(it) }

            if (task.includeExamples) {
                appendLine("\n## 5. EXAMPLES")
                appendLine(CopyWritingIdentityScripts.EXAMPLES)
            }
        }.trim()
    }

    private fun buildDomainMessage(domain: DomainContext): String {
        return buildString {
            appendLine("## BUSINESS DOMAIN CONTEXT")
            appendLine("You must perform the task with a deep understanding of the following business environment:")

            appendLine("\n### 1. Industry Category")
            appendLine("- **Sector**: ${domain.businessType}")
            appendLine("- **Guideline**: Use terminology and communication styles standard to the ${domain.businessType} industry.")

            appendLine("\n### 2. Core Subject")
            appendLine("- **Primary Product/Service**: ${domain.productName}")
            appendLine("- **Guideline**: Ensure the unique value proposition of '${domain.productName}' is clearly highlighted throughout the output.")
        }.trim()
    }

    private fun buildDesignBriefMessage(brief: DesignBriefContext): String {
        return buildString {
            appendLine("## DESIGN SPECIFICATIONS")
            appendLine("Create a complete, professional Instagram advertisement with the following specifications:")

            appendLine("\n### Marketing Copy (Korean Text to Include in Design)")
            appendLine("\"${brief.copyWrite}\"")

            appendLine("\n### Target Audience")
            appendLine("- ${brief.targetAudience}")

            appendLine("\n### Brand Style Direction")
            appendLine("- ${brief.brandStyle}")
            appendLine("- Choose or blend from the style examples provided in the system context")

            appendLine("\n### Technical Specifications")
            appendLine("- Size: 1024x1024px (Instagram square post)")
            appendLine("- Korean text must be professionally integrated into the design")
            appendLine("- Keep critical elements away from Instagram UI zones (top 15%, bottom 20%)")

            appendLine("\n### Design Requirements")
            appendLine("- The Korean text \"${brief.copyWrite}\" must be visible and beautifully designed in the image")
            appendLine("- Think: complete finished advertisement, not a background for overlay")
            appendLine("- Professional typography with proper hierarchy and spacing")
            appendLine("- High contrast and readability for mobile viewing")
            appendLine("- Thumb-stopping visual impact for Instagram feeds")
        }.trim()
    }
}
