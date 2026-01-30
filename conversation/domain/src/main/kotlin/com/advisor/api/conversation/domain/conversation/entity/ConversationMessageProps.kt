package com.advisor.api.conversation.domain.conversation.entity

import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.conversation.domain.conversation.vo.MessageRole
import java.time.Instant

data class ConversationMessageProps(
    val conversationId: ConversationId,
    val memberId: MemberId,
    val role: MessageRole,
    val body: String,
    val revisionOf: ConversationMessageId? = null,
    val parentMessageId: ConversationMessageId? = null,
    val createdAt: Instant
)
