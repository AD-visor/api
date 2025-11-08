package com.advisor.api.subscription.infrastructure.plan

import org.springframework.data.jpa.repository.JpaRepository

interface PlanJpaReader: JpaRepository<PlanViewEntity, Long>
