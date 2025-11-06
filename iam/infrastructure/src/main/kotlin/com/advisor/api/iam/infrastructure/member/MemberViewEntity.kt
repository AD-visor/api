package com.advisor.api.iam.infrastructure.member

import com.advisor.api.iam.domain.member.MemberView
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.Immutable
import java.time.Instant

@Entity
@Immutable
@Table(name = "vw_member")
class MemberViewEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val createdAt: Instant,
) {
    companion object {
        fun fromModel(model: MemberViewEntity): MemberViewEntity {
            return MemberViewEntity(
                id = model.id,
                email = model.email,
                createdAt = model.createdAt,
            )
        }
    }

    fun toModel(): MemberView {
        return MemberView(
            id = id,
            email = email,
            createdAt = createdAt,
        )
    }
}
