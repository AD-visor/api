package com.advisor.api.token_usage.adapter.outbound

import org.springframework.data.jpa.repository.JpaRepository

interface TokenUsageJpaStore: JpaRepository<TokenUsageEntity, Long>
