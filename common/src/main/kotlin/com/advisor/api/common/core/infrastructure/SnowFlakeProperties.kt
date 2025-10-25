package com.advisor.api.common.core.infrastructure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "snowflake")
data class SnowFlakeProperties (
    val datacenterId: Long = 0,
    val workerId: Long = 0
)
