package com.advisor.api.media.adapter.outbound

import com.advisor.api.media.port.outbound.FileStoragePort
import org.springframework.stereotype.Component
import java.io.File

@Component
class LocalFileStorageAdapter: FileStoragePort {
    override fun save(name: String, bytes: ByteArray): String {
        val path = "uploads/$name"
        File(path).writeBytes(bytes)
        return path
    }
}
