package com.advisor.api.conversation.application.prompt

import com.advisor.api.ai_prompt_core.context.ConversationContext
import com.advisor.api.ai_prompt_core.context.DomainContext
import com.advisor.api.ai_prompt_core.context.TaskContext
import com.advisor.api.ai_prompt_core.context.UserContext
import com.advisor.api.ai_prompt_core.model.PromptMessage
import com.advisor.api.ai_prompt_core.phase.PromptPhase
import com.advisor.api.ai_prompt_core.phase.PromptPhaseProvider
import com.advisor.api.conversation.application.prompt.scripts.TaskScripts
import org.springframework.stereotype.Component

@Component
class UserContextProvider: PromptPhaseProvider<UserContext> {
    override val phase = PromptPhase.USER_CONTEXT

    override fun provide(context: UserContext): List<PromptMessage> {
        val messages = mutableListOf<PromptMessage>()

        val coreText = buildString {
            // 1. Task - 수행할 작업의 목적과 세부 내용
            context.task?.let {
                appendLine("# TASK")
                appendLine(buildTaskMessage(it))
            }

            // 2. Domain - Business의 분야 및 제품 정보
            context.domain?.let {
                appendLine("\n---\n")
                appendLine("# DOMAIN CONTEXT")
                appendLine(buildDomainMessage(it))
            }
        }.trim()

        // 3. Conversation - 대화 맥락
        context.conversation?.let { conversation ->
            val conversationText = buildString {
                appendLine("\n---\n")
                appendLine("# CONVERSATION HISTORY")
                appendLine("The following is the history of the conversation so far:")
            }.trim()

            messages.add(PromptMessage.user(conversationText))
            conversation.messages.map { messages.add(it) }
        }

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

            val finalSteps = task.steps.ifEmpty { TaskScripts.STEPS }
            appendLine("\n## 4. EXECUTION STEPS")
            finalSteps.forEach { appendLine("- $it") }
        }.trim()
    }

    private fun buildDomainMessage(domain: DomainContext): String {
        return buildString {
            appendLine("## BUSINESS DOMAIN CONTEXT")
            appendLine("You must perform the task with a deep understanding of the following business environment:")

            // 업종 정보: AI가 사용할 어휘와 톤의 범위를 결정
            appendLine("\n### 1. Industry Category")
            appendLine("- **Sector**: ${domain.businessType}")
            appendLine("- **Guideline**: Use terminology and communication styles standard to the ${domain.businessType} industry.")

            // 제품 정보: 모든 콘텐츠의 핵심 주제
            appendLine("\n### 2. Core Subject")
            appendLine("- **Primary Product/Service**: ${domain.productName}")
            appendLine("- **Guideline**: Ensure the unique value proposition of '${domain.productName}' is clearly highlighted throughout the output.")
        }.trim()
    }

    private fun buildConversationMessage(conversation: ConversationContext): String {
        return buildString {
            conversation.messages.forEach { appendLine("- $it") }
        }.trim()
    }
}
