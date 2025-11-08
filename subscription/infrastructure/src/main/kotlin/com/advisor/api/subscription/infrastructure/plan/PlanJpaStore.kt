package com.advisor.api.subscription.infrastructure.plan

import org.springframework.data.jpa.repository.JpaRepository

interface PlanJpaStore: JpaRepository<PlanEntity, Long>
