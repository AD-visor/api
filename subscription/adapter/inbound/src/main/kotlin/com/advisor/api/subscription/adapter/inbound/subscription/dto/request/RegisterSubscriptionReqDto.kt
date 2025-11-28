package com.advisor.api.subscription.adapter.inbound.subscription.dto.request

import com.advisor.api.subscription.port.inbound.subscription.command.RegisterSubscriptionCommand

data class RegisterSubscriptionReqDto(
    val planId: String,
    val term: String
) {
    fun toCommand(memberId: Long): RegisterSubscriptionCommand {
        return RegisterSubscriptionCommand(
            memberId = memberId,
            planId = planId.toLong(),
            paymentId = 2L,
            term = term
        )
    }
}
