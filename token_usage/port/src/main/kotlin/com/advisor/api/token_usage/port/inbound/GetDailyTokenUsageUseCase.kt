package com.advisor.api.token_usage.port.inbound

import com.advisor.api.token_usage.port.inbound.query.GetDailyTokenUsageQuery
import com.advisor.api.token_usage.port.inbound.view.DailyTokenUsageView

interface GetDailyTokenUsageUseCase {
    fun execute(query: GetDailyTokenUsageQuery): List<DailyTokenUsageView>
}
