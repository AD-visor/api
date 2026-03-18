package com.advisor.api.media.port.outbound

import com.advisor.api.media.port.outbound.command.ImageFetchCommand
import com.advisor.api.media.port.outbound.result.ImageFetchResult

interface ImageFetchPort {
    suspend fun fetch(command: ImageFetchCommand.Fetch): ImageFetchResult
}
