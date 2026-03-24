package com.advisor.api.token_usage.application

import com.advisor.api.token_usage.port.inbound.GetDailyTokenUsageUseCase
import com.advisor.api.token_usage.port.inbound.query.GetDailyTokenUsageQuery
import com.advisor.api.token_usage.port.inbound.view.DailyTokenUsageView
import com.advisor.api.token_usage.port.outbound.TokenUsageReader
import org.springframework.stereotype.Service

@Service
class GetDailyTokenUsageService(
    private val tokenUsageReader: TokenUsageReader
) : GetDailyTokenUsageUseCase {

    override fun execute(query: GetDailyTokenUsageQuery): List<DailyTokenUsageView> {
        return tokenUsageReader.findDailySummary(
            subscriptionId = query.subscriptionId,
            memberId = query.memberId,
            from = query.from,
            to = query.to
        )
    }
}
