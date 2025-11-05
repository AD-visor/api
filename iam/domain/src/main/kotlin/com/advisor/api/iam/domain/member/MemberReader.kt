package com.advisor.api.iam.domain.member

interface MemberReader {
    fun findById(id: Long): MemberView
}
