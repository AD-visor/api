package com.advisor.api.common.core.domain.vo

class Money private constructor(val value: Float) {
    init { validate() }

    companion object {
        fun create(value: Float): Money {
            return Money(value)
        }
    }

    fun validate() {
        require(value >= 0.0) { "[Money] 돈은 음수일 수 없습니다." }
    }

    operator fun plus(other: Money): Money = Money(this.value + other.value)
    operator fun minus(other: Money): Money = Money(this.value - other.value)

    override fun equals(other: Any?): Boolean = other is Money && this.value == other.value
    override fun hashCode(): Int = value.hashCode()
}
