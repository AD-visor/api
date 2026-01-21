package com.advisor.api.media.adapter.outbound

import com.advisor.api.common.exception.CustomException
import com.advisor.api.media.port.outbound.ImageFetchPort
import com.advisor.api.media.port.outbound.command.ImageFetchCommand
import com.advisor.api.media.port.outbound.result.ImageFetchResult
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class ImageFetchAdapter(private val restClient: RestClient) : ImageFetchPort {
    override fun fetch(command: ImageFetchCommand.Fetch): ImageFetchResult {
        return try {
            val imageBytes = restClient.get()
                .uri(command.imageUrl)
                .retrieve()
                .body(ByteArray::class.java)
                ?: throw CustomException(
                    MediaInfrastructureExceptionCode.MEDIA_FILE_NOT_FOUND,
                    "[Prompt] 이미지 다운로드 응답이 비어 있습니다."
                )

            return ImageFetchResult(imageBytes)
        } catch (e: Exception) { throw CustomException(
            MediaInfrastructureExceptionCode.MEDIA_FILE_DOWNLOAD_FAILURE,
            "[Prompt] 이미지 다운로드에 실패했습니다. ${e.message}"
        ) }
    }
}
