package com.advisor.api.token_usage.application

import com.advisor.api.token_usage.domain.TokenUsageReader
import com.advisor.api.token_usage.port.inbound.RefreshTokenUsageViewUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class RefreshTokenViewUsageService(
    private val tokenUsageReader: TokenUsageReader
): RefreshTokenUsageViewUseCase {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun execute() {
        tokenUsageReader.refreshView()
    }
}
