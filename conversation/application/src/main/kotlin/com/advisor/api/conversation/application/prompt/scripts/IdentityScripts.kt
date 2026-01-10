package com.advisor.api.conversation.application.prompt.scripts

import com.advisor.api.ai_prompt_core.model.PromptType
import com.advisor.api.conversation.application.prompt.scripts.identity.CopyWritingIdentityScripts
import com.advisor.api.conversation.application.prompt.scripts.identity.ImageGenerationIdentityScripts
import com.advisor.api.conversation.application.prompt.scripts.identity.LayoutAnalysisIdentityScripts

object IdentityScripts {
    fun instruction(promptType: PromptType): String {
        return when (promptType) {
            PromptType.COPY_WRITING -> CopyWritingIdentityScripts.INSTRUCTION
            PromptType.IMAGE_GENERATION -> ImageGenerationIdentityScripts.INSTRUCTION
            PromptType.LAYOUT_ANALYSIS -> LayoutAnalysisIdentityScripts.INSTRUCTION
        }
    }

    fun principles(promptType: PromptType): List<String> {
        return when (promptType) {
            PromptType.COPY_WRITING -> CopyWritingIdentityScripts.PRINCIPLES
            PromptType.IMAGE_GENERATION -> ImageGenerationIdentityScripts.PRINCIPLES
            PromptType.LAYOUT_ANALYSIS -> LayoutAnalysisIdentityScripts.PRINCIPLES
        }
    }

    fun constraints(promptType: PromptType): List<String> {
        return when (promptType) {
            PromptType.COPY_WRITING -> CopyWritingIdentityScripts.CONSTRAINTS
            PromptType.IMAGE_GENERATION -> ImageGenerationIdentityScripts.CONSTRAINTS
            PromptType.LAYOUT_ANALYSIS -> LayoutAnalysisIdentityScripts.CONSTRAINTS
        }
    }
}
