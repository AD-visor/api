package com.advisor.api.subscription.infrastructure.plan

import com.advisor.api.common.core.domain.vo.Money
import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.subscription.domain.plan.Plan
import com.advisor.api.subscription.domain.plan.PlanProps
import com.advisor.api.subscription.domain.plan.vo.MonthlyLimit
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "plan")
class PlanEntity(
    @Id
    val id: Long,

    @Column
    val name: String,

    @Column
    val monthlyLimit: Long,

    @Column
    val price: Float,

    @Column
    val description: String?,

    @Column
    val createdAt: Instant,

    @Column
    val updatedAt: Instant,

    @Column
    val isDeleted: Boolean,

    @Column
    val deletedAt: Instant?,
) {
    companion object {
        fun fromDomain(domain: Plan): PlanEntity {
            return PlanEntity(
                id = domain.id.value,
                name = domain.name,
                monthlyLimit = domain.monthlyLimit.value,
                price = domain.price.value,
                description = domain.description,
                createdAt = domain.createdAt,
                updatedAt = domain.updatedAt,
                isDeleted = domain.isDeleted,
                deletedAt = domain.deletedAt
            )
        }
    }

    fun toDomain(): Plan {
        val props = PlanProps(
            name = name,
            monthlyLimit = MonthlyLimit.create(monthlyLimit),
            price = Money.create(price),
            description = description,
            createdAt = createdAt,
            updatedAt = updatedAt,
            isDeleted = isDeleted,
            deletedAt = deletedAt
        )

        return Plan.of(PlanId(id), props)
    }
}
