package com.advisor.api.iam.adapter.outbound.auth

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AuthJpaStore: JpaRepository<AuthEntity, Long> {
    fun findByMemberId(memberId: Long): Optional<AuthEntity>
}
