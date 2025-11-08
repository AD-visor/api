package com.advisor.api.subscription.infrastructure.token_usage

import com.advisor.api.common.core.domain.vo.identifier.TokenUsageId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.subscription.domain.token_usage.TokenUsage
import com.advisor.api.subscription.domain.token_usage.TokenUsageStore
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
