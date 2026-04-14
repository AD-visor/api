package com.advisor.api.conversation.adapter.inbound.conversation.dto.request

import com.advisor.api.conversation.port.inbound.command.UpdateConversationCommand

data class UpdateConversationReqDto(
    val businessType: String?,
    val productName: String?,
    val description: String?,
    val targetAudience: String?,
    val toneStyle: String?,
    val platform: String?,
) {
    fun toCommand(id:Long, memberId: Long): UpdateConversationCommand {
        return UpdateConversationCommand(
            id = id,
            memberId = memberId,
            businessType = businessType,
            productName = productName,
            description = description,
            targetAudience = targetAudience,
            toneStyle = toneStyle,
            platform = platform,
        )
    }
}
