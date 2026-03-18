package com.advisor.api.payment.adapter.outbound.payment

import com.advisor.api.common.core.domain.vo.identifier.PaymentId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.payment.domain.payment.Payment
import com.advisor.api.payment.port.outbound.PaymentStore
import org.springframework.stereotype.Repository

@Repository
class PaymentStoreImpl(
    private val jpaStore: PaymentJpaStore
): PaymentStore {
    override fun save(payment: Payment) {
        val entity = PaymentEntity.fromDomain(payment)
        jpaStore.save(entity)
    }

    override fun loadById(id: PaymentId): Payment {
        val entity = jpaStore.findById(id.value).orElseThrow { CustomException(
            PaymentInfraExceptionCode.PAYMENT_NOT_FOUND,
            "[Payment] id=${id.value}에 해당하는 결제 정보를 찾을 수 없습니다."
        ) }

        return entity.toDomain()
    }
}
