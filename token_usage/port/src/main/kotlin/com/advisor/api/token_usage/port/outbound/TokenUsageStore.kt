package com.advisor.api.token_usage.port.outbound

import com.advisor.api.common.core.domain.vo.identifier.TokenUsageId
import com.advisor.api.token_usage.domain.TokenUsage

interface TokenUsageStore {
    fun save(tokenUsage: TokenUsage)
    fun loadById(id: TokenUsageId): TokenUsage
}
