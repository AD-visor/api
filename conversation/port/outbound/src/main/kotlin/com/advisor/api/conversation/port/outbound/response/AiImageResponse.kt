package com.advisor.api.conversation.port.outbound.response

data class AiImageResponse(
    val bytes: ByteArray,
    val count: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AiImageResponse

        if (count != other.count) return false
        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = count
        result = 31 * result + bytes.contentHashCode()
        return result
    }
}
