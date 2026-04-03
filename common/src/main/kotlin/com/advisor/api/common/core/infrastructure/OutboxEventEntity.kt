package com.advisor.api.common.core.infrastructure

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "outbox_event")
class OutboxEventEntity(
    @Id
    val id: String,

    @Column(nullable = false)
    val aggregateType: String,

    @Column(nullable = false)
    val aggregateId: String,

    @Column(nullable = false)
    val eventType: String,

    @Column(columnDefinition = "jsonb")
    val payload: String,

    @Column(nullable = false)
    val createdAt: Instant = Instant.now()
)
