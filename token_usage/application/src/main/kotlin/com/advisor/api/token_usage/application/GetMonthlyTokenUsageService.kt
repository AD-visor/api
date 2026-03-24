package com.advisor.api.token_usage.application

import com.advisor.api.token_usage.port.inbound.GetMonthlyTokenUsageUseCase
import com.advisor.api.token_usage.port.inbound.query.GetMonthlyTokenUsageQuery
import com.advisor.api.token_usage.port.inbound.view.MonthlyTokenUsageView
import com.advisor.api.token_usage.port.outbound.TokenUsageReader
import org.springframework.stereotype.Service

@Service
class GetMonthlyTokenUsageService(
    private val tokenUsageReader: TokenUsageReader
) : GetMonthlyTokenUsageUseCase {
    override fun execute(query: GetMonthlyTokenUsageQuery): List<MonthlyTokenUsageView> {
        return tokenUsageReader.findMonthlySummary(
            subscriptionId = query.subscriptionId,
            memberId = query.memberId,
            month = query.month
        )
    }
}
