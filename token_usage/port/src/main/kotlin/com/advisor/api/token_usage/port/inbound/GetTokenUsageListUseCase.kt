package com.advisor.api.token_usage.port.inbound

import com.advisor.api.token_usage.port.inbound.query.GetTokenUsageListQuery
import com.advisor.api.token_usage.port.inbound.view.TokenUsageView

interface GetTokenUsageListUseCase {
    fun execute(query: GetTokenUsageListQuery): List<TokenUsageView>
}
