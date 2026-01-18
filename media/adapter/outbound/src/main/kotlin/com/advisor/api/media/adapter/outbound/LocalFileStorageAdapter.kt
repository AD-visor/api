package com.advisor.api.media.adapter.outbound

import com.advisor.api.common.exception.CustomException
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

    override fun delete(path: String) {
        val file = File(path)
        if (file.exists()) {
            val deleted = file.delete()
            if (!deleted) throw CustomException(
                MediaInfrastructureExceptionCode.MEDIA_FILE_DELETE_FAILURE,
                "[Media] 미디어 파일 삭제에 실패했습니다."
            )
        }
    }
}
