package com.advisor.api.media.port.outbound

interface FileStoragePort {
    fun save(name: String, bytes: ByteArray): String
}
