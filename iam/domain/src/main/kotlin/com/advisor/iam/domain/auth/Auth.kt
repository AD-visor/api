package com.advisor.iam.domain.auth

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.vo.identifier.AuthId
import com.advisor.api.common.core.domain.vo.identifier.UserId
import com.advisor.iam.domain.auth.vo.OAuthCredential
import com.advisor.iam.domain.auth.vo.RefreshToken
import java.time.Instant

class Auth(
    id: AuthId,
    private val props: AuthProps
): AggregateRoot<AuthId>(id) {
    init { validate() }

    companion object {
        fun create(id: AuthId, props: AuthProps): Auth {
            return Auth(id, props)
        }

        fun of(id: AuthId, props: AuthProps): Auth {
            return Auth(id, props)
        }
    }

    private fun validate() {}

    fun updateRefreshToken(newRefreshToken: RefreshToken): Auth {
        return Auth(id, props.copy(
            refreshToken = newRefreshToken,
            updatedAt = Instant.now()
        ))
    }

    fun delete(): Auth {
        return Auth(id, props.copy(
            isDeleted = true,
            deletedAt = Instant.now()
        ))
    }

    fun unDelete(): Auth {
        return Auth(id, props.copy(
            isDeleted = false,
            deletedAt = null
        ))
    }

    val refreshToken: RefreshToken get() = props.refreshToken
    val oAuthCredential: OAuthCredential get() = props.oAuthCredential
    val userId: UserId get() = props.userId
    val createdAt: Instant get() = props.createdAt
    val updatedAt: Instant get() = props.updatedAt
    val isDeleted: Boolean get() = props.isDeleted
    val deletedAt: Instant? get() = props.deletedAt
}
