package com.advisor.api.subscription.adapter.outbound.plan

import org.springframework.data.jpa.repository.JpaRepository

interface PlanJpaStore: JpaRepository<PlanEntity, Long>
