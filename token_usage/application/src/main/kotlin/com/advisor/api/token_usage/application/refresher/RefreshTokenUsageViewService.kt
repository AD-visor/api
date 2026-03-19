package com.advisor.api.token_usage.application.refresher

import com.advisor.api.token_usage.port.outbound.TokenUsageReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
internal class RefreshTokenUsageViewService(
    private val tokenUsageReader: TokenUsageReader
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun execute() {
        tokenUsageReader.refreshView()
    }
}
