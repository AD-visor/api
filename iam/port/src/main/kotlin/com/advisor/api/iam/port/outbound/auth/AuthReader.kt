package com.advisor.api.iam.port.outbound.auth

import com.advisor.api.iam.port.inbound.auth.view.AuthView

interface AuthReader {
    fun findByMemberId(memberId: Long): AuthView
}
