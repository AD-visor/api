package com.advisor.api.app.config.security.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "frontend")
data class FrontendProperties(
    val url: String
)
