package com.advisor.api.media.domain

import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.media.domain.vo.MediaPath
import com.advisor.api.media.domain.vo.MediaType
import com.advisor.api.media.domain.vo.MimeType
import java.time.Instant

data class MediaProps(
    val path: MediaPath,
    val mediaType: MediaType,
    val mimeType: MimeType,
    val conversationId: ConversationId,
    val conversationMessageId: ConversationMessageId,
    val memberId: MemberId,
    val width: Int,
    val height: Int,
    val fileSize: Long,
    val createdAt: Instant = Instant.now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)
