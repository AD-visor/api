package com.advisor.api.iam.adapter.outbound.auth

import com.advisor.api.iam.port.outbound.auth.AuthReader
import com.advisor.api.iam.port.inbound.auth.view.AuthView
import org.springframework.stereotype.Repository


@Repository
class AuthReaderImpl(
    //private val authViewMapper: AuthViewMapper
): AuthReader {
    override fun findByMemberId(memberId: Long): AuthView {
        TODO()
        /*
        val authEntity = authViewMapper.findByMemberId(memberId).orElseThrow{ CustomException(
            AuthInfrastructureExceptionCode.AUTH_NOT_FOUND,
            "[Auth] ${memberId}에 해당하는 유저의 인증 정보가 존재하지 않습니다."
        ) }

        return AuthViewEntity.toModel(authEntity)
        */
    }
}
