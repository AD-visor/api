package com.advisor.api.media.domain

import com.advisor.api.common.core.domain.vo.identifier.MediaId

interface MediaStore {
    fun save(media: Media)
    fun saveAll(medias: List<Media>)
    fun loadById(id: MediaId): Media
}
