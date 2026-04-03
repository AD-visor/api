package com.advisor.api.common.core.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface OutboxEventJpaStore: JpaRepository<OutboxEventEntity, Long>
