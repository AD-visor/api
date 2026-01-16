package com.advisor.api.media.port.inbound.command

class CreateMediaCommand(
    val conversationId: Long,
    val conversationMessageId: Long,
    val mimeType: String,
    val file: ByteArray,
    val width: Int,
    val height: Int
) {
    val fileSize = file.size.toLong()
}
