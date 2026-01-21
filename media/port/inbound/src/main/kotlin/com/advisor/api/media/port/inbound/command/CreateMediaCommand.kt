package com.advisor.api.media.port.inbound.command

data class CreateMediaCommand(
    val conversationId: Long,
    val conversationMessageId: Long,
    val memberId: Long,
    val mimeType: String,
    val file: ByteArray,
    val width: Int,
    val height: Int
) {
    val fileSize = file.size.toLong()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CreateMediaCommand

        if (conversationId != other.conversationId) return false
        if (conversationMessageId != other.conversationMessageId) return false
        if (memberId != other.memberId) return false
        if (width != other.width) return false
        if (height != other.height) return false
        if (fileSize != other.fileSize) return false
        if (mimeType != other.mimeType) return false
        if (!file.contentEquals(other.file)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = conversationId.hashCode()
        result = 31 * result + conversationMessageId.hashCode()
        result = 31 * result + memberId.hashCode()
        result = 31 * result + width
        result = 31 * result + height
        result = 31 * result + fileSize.hashCode()
        result = 31 * result + mimeType.hashCode()
        result = 31 * result + file.contentHashCode()
        return result
    }
}
