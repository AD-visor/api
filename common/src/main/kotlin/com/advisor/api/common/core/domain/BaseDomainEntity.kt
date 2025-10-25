package com.advisor.api.common.core.domain

import com.advisor.api.common.core.domain.vo.Identifier
import java.io.Serializable

abstract class BaseDomainEntity<ID : Identifier<out Serializable>>(val id: ID) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as BaseDomainEntity<*>
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
