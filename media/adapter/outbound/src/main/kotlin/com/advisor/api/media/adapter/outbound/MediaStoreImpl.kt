package com.advisor.api.media.adapter.outbound

import com.advisor.api.common.core.domain.vo.identifier.MediaId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.media.domain.Media
import com.advisor.api.media.port.outbound.MediaStore
import org.springframework.stereotype.Repository

@Repository
class MediaStoreImpl(
    private val mediaJpaStore: MediaJpaStore
): MediaStore {
    override fun save(media: Media) {
        val entity = MediaEntity.fromDomain(media)
        mediaJpaStore.save(entity)
    }

    override fun saveAll(medias: List<Media>) {
        val entities = medias.map { MediaEntity.fromDomain(it) }
        mediaJpaStore.saveAll(entities)
    }

    override fun deleteByIdAndMemberId(
        id: MediaId,
        memberId: MemberId
    ) {
        mediaJpaStore.deleteByIdAndMemberId(id.value, memberId.value)
    }

    override fun loadById(id: MediaId): Media {
        val entity = mediaJpaStore.findById(id.value).orElseThrow {
            CustomException(
                MediaInfrastructureExceptionCode.MEDIA_NOT_FOUND,
                "[Media] id: ${id.value} 에 해당하는 미디어를 찾을 수 없습니다."
            )
        }

        return entity.toDomain()
    }

    override fun loadByIdAndMemberId(
        id: MediaId,
        memberId: MemberId
    ): Media {
        val entity = mediaJpaStore.findByIdAndMemberId(id.value, memberId.value).orElseThrow {
            CustomException(
                MediaInfrastructureExceptionCode.MEDIA_NOT_FOUND,
                "[Media] id: ${id.value} 에 해당하는 미디어를 찾을 수 없습니다."
            )
        }

        return entity.toDomain()
    }
}
