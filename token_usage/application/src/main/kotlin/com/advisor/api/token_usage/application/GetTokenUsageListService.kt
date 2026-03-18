package com.advisor.api.token_usage.application

import com.advisor.api.token_usage.port.outbound.TokenUsageReader
import com.advisor.api.token_usage.port.inbound.GetTokenUsageListUseCase
import com.advisor.api.token_usage.port.inbound.query.GetTokenUsageListQuery
import com.advisor.api.token_usage.port.inbound.view.TokenUsageView
import org.springframework.stereotype.Service

@Service
class GetTokenUsageListService(
    private val tokenUsageReader: TokenUsageReader
): GetTokenUsageListUseCase {
    override fun execute(query: GetTokenUsageListQuery): List<TokenUsageView> {
        return tokenUsageReader.findTokenUsageViewsByMemberId(
            query.memberId,
            query.startAt,
            query.endAt
        )
    }
}
