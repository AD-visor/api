package com.advisor.iam.infrastructure.auth

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AuthJpaRepository: JpaRepository<AuthEntity, Long> {
    fun findByMemberId(memberId: Long): Optional<AuthEntity>
}
