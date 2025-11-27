package com.advisor.api.payment.adapter.outbound.payment

import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaStore: JpaRepository<PaymentEntity, Long>
