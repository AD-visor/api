package com.advisor.iam.domain.member

interface MemberReader {
    fun findById(id: Long): MemberView
}
