package com.advisor.api.conversation.domain.conversation

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessage
import com.advisor.api.conversation.domain.conversation.vo.ContentPlatform
import com.advisor.api.conversation.domain.conversation.vo.SpeechStyle
import com.advisor.api.conversation.domain.conversation.vo.ToneStyle
import java.time.Instant

data class ConversationProps(
    val memberId: MemberId,
    val businessType: String,
    val productName: String,
    val description: String,
    val targetAudience: String,
    val toneStyle: ToneStyle,
    val speechStyle: SpeechStyle,
    val contentLength: String,
    val platform: ContentPlatform,
    val messages: List<ConversationMessage>,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isArchived: Boolean = false,
    val archivedAt: Instant? = null,
)
