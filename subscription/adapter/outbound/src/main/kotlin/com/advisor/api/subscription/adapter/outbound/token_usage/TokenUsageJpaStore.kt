package com.advisor.api.subscription.adapter.outbound.token_usage

import org.springframework.data.jpa.repository.JpaRepository

interface TokenUsageJpaStore: JpaRepository<TokenUsageEntity, Long>
