package com.advisor.api.media.adapter.outbound

import com.advisor.api.common.exception.CustomException
import com.advisor.api.media.port.outbound.ImageFetchPort
import com.advisor.api.media.port.outbound.command.ImageFetchCommand
import com.advisor.api.media.port.outbound.result.ImageFetchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.apache.hc.client5.http.classic.HttpClient
import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.core5.http.io.entity.EntityUtils
import org.springframework.stereotype.Component

@Component
class ImageFetchAdapter(
    private val httpClient: HttpClient
): ImageFetchPort {

    private val logger = KotlinLogging.logger {}

    override suspend fun fetch(command: ImageFetchCommand.Fetch): ImageFetchResult {
        val input = command.imageUrl

        return if (isBase64(input)) {
            decodeBase64Image(input)
        } else {
            fetchFromUrl(input)
        }
    }

    private fun isBase64(input: String): Boolean {
        return input.startsWith("data:image") || !input.startsWith("http")
    }

    private fun decodeBase64Image(base64String: String): ImageFetchResult {
        return try {
            val pureBase64 = if (base64String.contains(",")) {
                base64String.substringAfter(",")
            } else {
                base64String
            }

            val imageBytes = java.util.Base64.getDecoder().decode(pureBase64)

            logger.debug { "Base64 decoded. Size: ${imageBytes.size} bytes" }

            ImageFetchResult(imageBytes)
        } catch (e: Exception) {
            throw CustomException(
                MediaInfrastructureExceptionCode.MEDIA_FILE_DOWNLOAD_FAILURE,
                "Base64 디코딩 실패: ${e.message}"
            )
        }
    }

    private suspend fun fetchFromUrl(imageUrl: String): ImageFetchResult {
        return withContext(Dispatchers.IO) {
            logger.info { "이미지 다운로드 시작" }

            val request = HttpGet(imageUrl)
            httpClient.executeOpen(null, request, null).use { response ->
                val statusCode = response.code

                if (statusCode != 200) {
                    logger.error { "HTTP Error $statusCode during image download" }

                    throw CustomException(
                        MediaInfrastructureExceptionCode.MEDIA_FILE_DOWNLOAD_FAILURE,
                        "[Media] HTTP $statusCode - 이미지 다운로드 실패"
                    )
                }

                val entity = response.entity
                    ?: throw CustomException(
                        MediaInfrastructureExceptionCode.MEDIA_FILE_NOT_FOUND,
                        "[Media] 응답 본문이 없습니다 (entity is null)"
                    )

                val imageBytes = EntityUtils.toByteArray(entity)
                logger.info { "다운로드 완료: ${imageBytes.size} bytes" }

                ImageFetchResult(imageBytes)
            }
        }
    }
}
