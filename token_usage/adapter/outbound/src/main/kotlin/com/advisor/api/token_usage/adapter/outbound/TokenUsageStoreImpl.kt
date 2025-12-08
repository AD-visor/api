package com.advisor.api.token_usage.adapter.outbound

import com.advisor.api.common.core.domain.vo.identifier.TokenUsageId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.token_usage.domain.TokenUsage
import com.advisor.api.token_usage.domain.TokenUsageStore
import org.springframework.stereotype.Repository

@Repository
class TokenUsageStoreImpl(
    private val jpaStore: TokenUsageJpaStore
): TokenUsageStore {
    override fun save(tokenUsage: TokenUsage) {
        val entity = TokenUsageEntity.fromDomain(tokenUsage)
        jpaStore.save(entity)
    }

    override fun loadById(id: TokenUsageId): TokenUsage {
        val entity = jpaStore.findById(id.value).orElseThrow { CustomException(
            TokenUsageInfraExceptionCode.TOKEN_USAGE_NOT_FOUND,
            "[TokenUsage] id=${id.value}에 해당하는 토큰 사용 정보를 찾을 수 없습니다."
        ) }

        return entity.toDomain()
    }
}
