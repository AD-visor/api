package com.advisor.api.conversation.adapter.outbound.content

import com.advisor.api.common.core.domain.vo.identifier.ContentId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.content.Content
import com.advisor.api.conversation.domain.content.ContentStore
import org.springframework.stereotype.Repository

@Repository
class ContentStoreImpl(
    private val jpaStore: ContentJpaStore
): ContentStore {
    override fun save(content: Content) {
        val entity = ContentEntity.fromDomain(content)
        jpaStore.save(entity)
    }

    override fun loadById(id: ContentId): Content {
        val entity = jpaStore.findById(id.value).orElseThrow { CustomException(
            ContentInfraExceptionCode.CONTENT_NOT_FOUND,
            "[Content] id=${id.value}에 해당하는 콘텐츠를 찾을 수 없습니다."
        ) }

        return entity.toDomain()
    }
}
