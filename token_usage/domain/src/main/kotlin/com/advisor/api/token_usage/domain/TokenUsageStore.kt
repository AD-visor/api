package com.advisor.api.token_usage.domain

import com.advisor.api.common.core.domain.vo.identifier.TokenUsageId

interface TokenUsageStore {
    fun save(tokenUsage: TokenUsage)
    fun loadById(id: TokenUsageId): TokenUsage
}
