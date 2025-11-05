package com.advisor.api.iam.infrastructure.auth

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AuthJpaRepository: JpaRepository<com.advisor.api.iam.infrastructure.auth.AuthEntity, Long> {
    fun findByMemberId(memberId: Long): Optional<com.advisor.api.iam.infrastructure.auth.AuthEntity>
}
