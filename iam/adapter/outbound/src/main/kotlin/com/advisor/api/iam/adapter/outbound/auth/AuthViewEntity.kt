package com.advisor.api.iam.adapter.outbound.auth

import com.advisor.api.iam.domain.auth.AuthView
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

    @Column(nullable = false)
    val oAuthProvider: String,

    @Column(nullable = false)
    val createdAt: Instant
) {
    fun toModel(): AuthView {
        return AuthView(
            id = id,
            oAuthProvider = oAuthProvider,
            createdAt = createdAt
        )
    }
}
