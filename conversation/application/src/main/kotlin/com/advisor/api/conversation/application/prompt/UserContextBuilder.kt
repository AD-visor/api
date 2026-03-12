package com.advisor.api.conversation.application.prompt

import com.advisor.api.ai_prompt_core.context.DesignBriefContext
import com.advisor.api.ai_prompt_core.context.DomainContext
import com.advisor.api.ai_prompt_core.context.TaskContext
import com.advisor.api.ai_prompt_core.context.UserContext
import com.advisor.api.ai_prompt_core.model.PromptType
import com.advisor.api.conversation.application.prompt.scripts.TaskScripts
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.port.inbound.command.GeneratePromptCommand
import org.springframework.stereotype.Component

@Component
class UserContextBuilder(
    private val conversationContextBuilder: ConversationContextBuilder
) {
    fun build(command: GeneratePromptCommand): UserContext {
        return when (command.promptType) {
            PromptType.COPY_WRITING -> buildForCopyWriting(command)
            PromptType.INTEGRATED_DESIGN -> buildForIntegratedDesign(command)
            PromptType.DESIGN_CONCEPT -> buildForDesignConcept(command)
        }
    }

    private fun buildForCopyWriting(command: GeneratePromptCommand): UserContext {
        val task = TaskContext(
            objective = "Create ultra-concise, high-impact Korean advertising copy",
            description = command.userRequest,
            requirements = listOf(
                "Must be 5-15 Korean characters",
                "Must be visually striking as typography",
                "Must be instantly memorable",
                "Must convey single clear message"
            ),
            steps = listOf(
                "1. Identify the core emotional benefit",
                "2. Find the most powerful Korean words",
                "3. Test for visual impact and memorability",
                "4. Ensure brevity (5-15 characters)"
            ),
            includeExamples = true
        )

        return UserContext(
            task = task,
            domain = buildDomain(command.conversation),
            conversation = conversationContextBuilder.build(command.messages)
        )
    }

    private fun buildForIntegratedDesign(command: GeneratePromptCommand): UserContext {
        val task = TaskContext(
            objective = "Create a complete, professional Instagram advertisement with Korean text integrated",
            description = command.userRequest,
            steps = TaskScripts.INTEGRATED_DESIGN_STEPS
        )

        val designBrief = DesignBriefContext(
            copyWrite = command.additionalData["copyWrite"] as? String
                ?: throw IllegalArgumentException("copyWrite is required"),
            targetAudience = command.additionalData["targetAudience"] as? String
                ?: "20-30대 일반 소비자",
            brandStyle = command.additionalData["brandStyle"] as? String
                ?: "Minimalist"
        )

        return UserContext(
            task = task,
            domain = buildDomain(command.conversation),
            conversation = conversationContextBuilder.build(command.messages),
            designBrief = designBrief
        )
    }

    private fun buildForDesignConcept(command: GeneratePromptCommand): UserContext {
        val task = TaskContext(
            objective = TaskScripts.OBJECTIVE,
            description = command.userRequest,
            steps = TaskScripts.INTEGRATED_DESIGN_STEPS
        )

        return UserContext(
            task = task,
            domain = buildDomain(command.conversation),
            conversation = conversationContextBuilder.build(command.messages)
        )
    }

    private fun buildDomain(conversation: Conversation): DomainContext {
        return DomainContext(
            businessType = conversation.businessType,
            productName = conversation.productName
        )
    }
}
