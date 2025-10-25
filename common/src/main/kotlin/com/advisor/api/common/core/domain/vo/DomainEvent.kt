package com.advisor.api.common.core.domain.vo

import java.time.Instant

interface DomainEvent {
    val id: Identifier<Long>
    val createdAt: Instant
}
