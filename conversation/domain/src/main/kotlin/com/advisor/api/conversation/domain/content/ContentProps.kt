package com.advisor.api.conversation.domain.content

import com.advisor.api.common.core.domain.vo.identifier.ContentRequestId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.conversation.domain.content.vo.ContentPlatform
import java.time.Instant

data class ContentProps(
    val memberId: MemberId,
    val contentRequestId: ContentRequestId,
    val title: String,
    val body: String,
    val platform: ContentPlatform,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?
)
