package com.advisor.api.conversation.application.prompt.scripts

import com.advisor.api.ai_prompt_core.model.PromptType
import com.advisor.api.conversation.application.prompt.scripts.identity.CopyWritingIdentityScripts
import com.advisor.api.conversation.application.prompt.scripts.identity.DesignConceptIdentityScripts
import com.advisor.api.conversation.application.prompt.scripts.identity.IntegratedDesignIdentityScripts

object IdentityScripts {
    fun instruction(promptType: PromptType): String {
        return when (promptType) {
            PromptType.COPY_WRITING -> CopyWritingIdentityScripts.INSTRUCTION
            PromptType.INTEGRATED_DESIGN -> IntegratedDesignIdentityScripts.INSTRUCTION
            PromptType.DESIGN_CONCEPT -> DesignConceptIdentityScripts.INSTRUCTION
        }
    }

    fun principles(promptType: PromptType): List<String> {
        return when (promptType) {
            PromptType.COPY_WRITING -> CopyWritingIdentityScripts.PRINCIPLES
            PromptType.INTEGRATED_DESIGN -> IntegratedDesignIdentityScripts.PRINCIPLES
            PromptType.DESIGN_CONCEPT -> DesignConceptIdentityScripts.PRINCIPLES
        }
    }

    fun constraints(promptType: PromptType): List<String> {
        return when (promptType) {
            PromptType.COPY_WRITING -> CopyWritingIdentityScripts.CONSTRAINTS
            PromptType.INTEGRATED_DESIGN -> IntegratedDesignIdentityScripts.CONSTRAINTS
            PromptType.DESIGN_CONCEPT -> DesignConceptIdentityScripts.CONSTRAINTS
        }
    }
}
