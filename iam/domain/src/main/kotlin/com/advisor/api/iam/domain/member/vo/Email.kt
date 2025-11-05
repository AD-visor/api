package com.advisor.api.iam.domain.member.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.iam.domain.member.MemberDomainExceptionCode

data class Email(val value: String) {
    init { validate() }

    companion object {
        fun create(value: String): Email {
            return Email(value)
        }
    }

    private fun validate() {
        require(value.isNotBlank()) { throw CustomException(
            MemberDomainExceptionCode.USER_EMAIL_BLANK,
            "[Email] 이메일은 필수입니다."
        ) }

        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
        require(emailRegex.matches(value)) { throw CustomException(
            MemberDomainExceptionCode.USER_INVALID_EMAIL_FORMAT,
            "[Email] 유효하지 않은 이메일 형식입니다."
        ) }
    }
}
