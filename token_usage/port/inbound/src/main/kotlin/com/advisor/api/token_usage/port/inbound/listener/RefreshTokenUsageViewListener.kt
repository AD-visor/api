package com.advisor.api.token_usage.port.inbound.listener

import com.advisor.api.common.core.domain.vo.DomainEvent

interface RefreshTokenUsageViewListener {
    fun handle(event: DomainEvent)
}
