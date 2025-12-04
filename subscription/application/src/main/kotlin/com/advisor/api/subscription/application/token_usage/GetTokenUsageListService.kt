package com.advisor.api.subscription.application.token_usage

import com.advisor.api.subscription.domain.token_usage.TokenUsageReader
import com.advisor.api.subscription.port.inbound.token_usage.GetTokenUsageListUseCase
import com.advisor.api.subscription.port.inbound.token_usage.query.GetTokenUsageListQuery
import com.advisor.api.subscription.port.inbound.token_usage.result.GetTokenUsageResult
import org.springframework.stereotype.Service

@Service
class GetTokenUsageListService(
    private val tokenUsageReader: TokenUsageReader
): GetTokenUsageListUseCase {
    override fun execute(query: GetTokenUsageListQuery): List<GetTokenUsageResult> {
        val tokenUsages = tokenUsageReader.findTokenUsageViewsByMemberId(
            query.memberId,
            query.startAt,
            query.endAt
        )

        return tokenUsages.map { GetTokenUsageResult.fromModel(it) }
    }
}
