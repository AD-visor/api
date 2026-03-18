package com.advisor.api.conversation.port.inbound.prompt

import com.advisor.api.conversation.port.inbound.command.GeneratePromptCommand
import com.advisor.api.conversation.port.inbound.result.GeneratePromptResult

interface GeneratePromptUseCase {
    fun execute(command: GeneratePromptCommand): GeneratePromptResult
}
