package com.advisor.api.subscription.infrastructure.token_usage

import org.springframework.data.jpa.repository.JpaRepository

interface TokenUsageJpaStore: JpaRepository<TokenUsageEntity, Long> {
}
