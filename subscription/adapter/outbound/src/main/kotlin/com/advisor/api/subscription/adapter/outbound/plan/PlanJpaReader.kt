package com.advisor.api.subscription.adapter.outbound.plan

import org.springframework.data.jpa.repository.JpaRepository

interface PlanJpaReader: JpaRepository<PlanViewEntity, Long>
