package com.advisor.api.conversation.domain.content

import java.time.Instant

data class ContentView(
    val id: Long,
    val memberId: Long,
    val contentRequestId: Long,
    val title: String,
    val body: String,
    val platform: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?
)
