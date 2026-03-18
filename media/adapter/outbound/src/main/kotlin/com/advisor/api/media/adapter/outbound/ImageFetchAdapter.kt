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
import org.apache.hc.client5.http.impl.classic.HttpClients
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
        return input.startsWith("data:image") || input.length > 5000
    }

    private fun decodeBase64Image(base64String: String): ImageFetchResult {
        return try {
            logger.info { "        📦 Base64 데이터 디코딩 시작" }

            val pureBase64 = if (base64String.contains(",")) {
                base64String.substringAfter(",")
            } else {
                base64String
            }

            val imageBytes = java.util.Base64.getDecoder().decode(pureBase64)
            logger.info { "        ✅ 디코딩 완료: ${imageBytes.size} bytes" }

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
            logger.info { "        📥 이미지 다운로드 시작" }

            // ✅ URL을 그대로 사용 (재인코딩 없음)
            val request = HttpGet(imageUrl)
            val response = httpClient.executeOpen(null, request, null)

            response.use { response ->
                val statusCode = response.code

                if (statusCode != 200) {
                    val body = try {
                        EntityUtils.toString(response.entity)
                    } catch (e: Exception) {
                        "응답 본문 읽기 실패"
                    }

                    logger.error {
                        """
                    ❌ HTTP 에러
                    - 상태 코드: $statusCode
                    - 응답: ${body.take(300)}
                    """.trimIndent()
                    }

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
                logger.info { "        ✅ 다운로드 완료: ${imageBytes.size} bytes" }

                ImageFetchResult(imageBytes)
            }
        }
    }
}
