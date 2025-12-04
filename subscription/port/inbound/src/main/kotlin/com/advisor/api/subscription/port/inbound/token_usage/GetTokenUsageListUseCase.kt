package com.advisor.api.subscription.port.inbound.token_usage

import com.advisor.api.subscription.port.inbound.token_usage.query.GetTokenUsageListQuery
import com.advisor.api.subscription.port.inbound.token_usage.result.GetTokenUsageResult

interface GetTokenUsageListUseCase {
    fun execute(query: GetTokenUsageListQuery): List<GetTokenUsageResult>
}
