package com.advisor.api.subscription.adapter.outbound.plan

import com.advisor.api.subscription.domain.plan.PlanView
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.Immutable
import java.time.Instant

@Entity
@Immutable
@Table(name = "vw_plan")
class PlanViewEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val monthlyLimit: Long,

    @Column(nullable = false)
    val price: Float,

    @Column
    val description: String?,

    @Column(nullable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val updatedAt: Instant,
) {
    fun toModel(): PlanView {
        return PlanView(
            id = id,
            name = name,
            monthlyLimit = monthlyLimit,
            price = price,
            description = description,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
