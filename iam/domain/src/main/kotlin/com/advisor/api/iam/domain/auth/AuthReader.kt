package com.advisor.api.iam.domain.auth

interface AuthReader {
    fun findByMemberId(memberId: Long): AuthView
}
