package com.advisor.api.subscription.domain.token_usage

import com.advisor.api.common.core.domain.vo.identifier.TokenUsageId

interface TokenUsageStore {
    fun save(tokenUsage: TokenUsage)
    fun loadById(id: TokenUsageId): TokenUsage
}
