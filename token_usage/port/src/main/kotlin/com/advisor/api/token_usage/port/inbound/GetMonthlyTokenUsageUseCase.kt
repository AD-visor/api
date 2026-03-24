package com.advisor.api.token_usage.port.inbound

import com.advisor.api.token_usage.port.inbound.query.GetMonthlyTokenUsageQuery
import com.advisor.api.token_usage.port.inbound.view.MonthlyTokenUsageView

interface GetMonthlyTokenUsageUseCase {
    fun execute(query: GetMonthlyTokenUsageQuery): List<MonthlyTokenUsageView>
}
