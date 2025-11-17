package com.advisor.api.payment.infrastructure.payment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface PaymentJpaReader: JpaRepository<PaymentViewEntity, Long> {
    @Query("""
        SELECT p
        FROM PaymentViewEntity p
        WHERE p.memberId = :memberId
        AND (:start_at IS NULL OR p.requestedAt >= :start_at)
        AND (:end_at IS NULL OR p.requestedAt <= :end_at)
    """)
    fun findAllByMemberId(
        @Param("memberId") memberId: Long,
        @Param("start_at") startAt: Instant?,
        @Param("end_at") endAt: Instant?,
    ): List<PaymentViewEntity>
}
