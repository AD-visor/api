package com.advisor.api.iam.infrastructure.auth

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.exception.CustomException
import com.advisor.iam.domain.auth.Auth
import com.advisor.iam.domain.auth.AuthStore
import org.springframework.stereotype.Repository

@Repository
class AuthStoreImpl(
    private val authJpaRepository: com.advisor.api.iam.infrastructure.auth.AuthJpaRepository
): AuthStore {
    override fun save(auth: Auth) {
        val jpaEntity = com.advisor.api.iam.infrastructure.auth.AuthEntity.Companion.toPersistence(auth)
        authJpaRepository.save(jpaEntity)
    }

    override fun load(id: MemberId): Auth {
        val jpaEntity = authJpaRepository.findByMemberId(id.value).orElseThrow { CustomException(
            com.advisor.api.iam.infrastructure.auth.AuthInfrastructureExceptionCode.AUTH_NOT_FOUND,
            "[Auth] ${id}에 해당하는 유저의 인증 정보가 존재하지 않습니다."
        ) }

        return com.advisor.api.iam.infrastructure.auth.AuthEntity.Companion.toDomain(jpaEntity)
    }
}
