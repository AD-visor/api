package com.advisor.api.conversation.domain.content

import com.advisor.api.common.core.domain.vo.identifier.ContentId

interface ContentStore {
    fun save(content: Content)
    fun loadById(id: ContentId): Content
}
