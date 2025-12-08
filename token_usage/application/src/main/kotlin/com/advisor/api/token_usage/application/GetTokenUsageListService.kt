package com.advisor.api.token_usage.application


import com.advisor.api.token_usage.domain.TokenUsageReader
import com.advisor.api.token_usage.port.inbound.GetTokenUsageListUseCase
import com.advisor.api.token_usage.port.inbound.query.GetTokenUsageListQuery
import com.advisor.api.token_usage.port.inbound.result.GetTokenUsageResult
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
