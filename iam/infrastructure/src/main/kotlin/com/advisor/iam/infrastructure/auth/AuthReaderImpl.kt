package com.advisor.iam.infrastructure.auth

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.exception.CustomException
import com.advisor.iam.domain.auth.AuthReader
import com.advisor.iam.domain.auth.AuthView
import org.springframework.stereotype.Repository

@Repository
class AuthReaderImpl(
    private val authViewMapper: AuthViewMapper
): AuthReader {
    override fun findByMemberId(memberId: MemberId): AuthView {
        val authEntity = authViewMapper.findByMemberId(memberId.value).orElseThrow{ CustomException(
            AuthInfrastructureExceptionCode.AUTH_NOT_FOUND,
            "[Auth] ${memberId}에 해당하는 유저의 인증 정보가 존재하지 않습니다."
        ) }

        return AuthViewEntity.toModel(authEntity)
    }
}
