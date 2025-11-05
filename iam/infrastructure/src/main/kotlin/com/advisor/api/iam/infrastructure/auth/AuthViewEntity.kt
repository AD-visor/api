package com.advisor.api.iam.infrastructure.auth

import com.advisor.iam.domain.auth.AuthView
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.Immutable
import java.time.Instant

@Entity
@Immutable
@Table(name = "vw_auth")
class AuthViewEntity (
    @Id
    val id: Long,

    @Column
    val oAuthProvider: String,

    @Column
    val createdAt: Instant
) {
    companion object {
        fun toModel(entity: AuthViewEntity): AuthView {
            return AuthView(
                id = entity.id,
                oAuthProvider = entity.oAuthProvider,
                createdAt = entity.createdAt
            )
        }

        fun toPersistence(model: AuthView): AuthViewEntity {
            return AuthViewEntity(
                id = model.id,
                oAuthProvider = model.oAuthProvider,
                createdAt = model.createdAt
            )
        }
    }
}
