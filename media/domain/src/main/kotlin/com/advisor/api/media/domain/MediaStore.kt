package com.advisor.api.media.domain

import com.advisor.api.common.core.domain.vo.identifier.MediaId
import com.advisor.api.common.core.domain.vo.identifier.MemberId

interface MediaStore {
    fun save(media: Media)
    fun saveAll(medias: List<Media>)
    fun deleteByIdAndMemberId(id: MediaId, memberId: MemberId)
    fun loadById(id: MediaId): Media
    fun loadByIdAndMemberId(id: MediaId, memberId: MemberId): Media
}
