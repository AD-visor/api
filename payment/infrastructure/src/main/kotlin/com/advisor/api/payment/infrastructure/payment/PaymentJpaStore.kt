package com.advisor.api.payment.infrastructure.payment

import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaStore: JpaRepository<PaymentEntity, Long>
