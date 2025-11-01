package com.advisor.iam.domain.auth

interface AuthReader {
    fun findByMemberId(memberId: Long): AuthView
}
