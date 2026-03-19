package com.advisor.api.media.port.inbound.command

data class DeleteMediaCommand(
    val mediaId: Long,
    val memberId: Long
)
