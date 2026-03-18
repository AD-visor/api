package com.advisor.api.conversation.adapter.outbound.conversation.mapper

import com.advisor.api.conversation.adapter.outbound.conversation.projection.ConversationMetadataProjection
import com.advisor.api.conversation.port.inbound.view.ConversationMetadataView
import org.springframework.stereotype.Component

@Component
class ConversationMapper {
    fun toMetadataView(projection: ConversationMetadataProjection): ConversationMetadataView {
        return ConversationMetadataView(
            id = projection.getId(),
            productName = projection.getProductName(),
            updatedAt = projection.getUpdatedAt()
        )
    }
}
