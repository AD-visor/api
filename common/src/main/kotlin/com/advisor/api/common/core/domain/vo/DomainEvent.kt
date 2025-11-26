package com.advisor.api.common.core.domain.vo

import com.advisor.api.common.core.domain.vo.identifier.DomainEventId
import java.time.Instant
import java.util.*

abstract class DomainEvent(
    val id: DomainEventId = DomainEventId(UUID.randomUUID().toString()),
    val createdAt: Instant = Instant.now()
)
