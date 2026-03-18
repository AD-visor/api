package com.advisor.api.media.adapter.outbound

import com.advisor.api.common.exception.CustomException
import com.advisor.api.media.port.outbound.FileStoragePort
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.io.File

@Component
class LocalFileStorageAdapter: FileStoragePort {
    private val logger = KotlinLogging.logger {}
    private val uploadDir = "uploads"

    override fun save(name: String, bytes: ByteArray): String {
        try {
            val safeName = File(name).name
            val path = "$uploadDir/$safeName"
            val file = File(path)

            file.parentFile?.mkdirs()

            file.writeBytes(bytes)

            logger.info { "파일 저장 완료: $path (${bytes.size} bytes)" }

            return path

        } catch (e: Exception) {
            logger.error(e) { "파일 저장 실패: $name" }
            throw CustomException(
                MediaInfrastructureExceptionCode.MEDIA_FILE_SAVE_FAILURE,
                "[Media] 파일 저장 실패: ${e.message}"
            )
        }
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
