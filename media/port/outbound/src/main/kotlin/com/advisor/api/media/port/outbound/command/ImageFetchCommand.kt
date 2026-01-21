package com.advisor.api.media.port.outbound.command

class ImageFetchCommand {
    data class Fetch(
        val imageUrl: String
    )
}
