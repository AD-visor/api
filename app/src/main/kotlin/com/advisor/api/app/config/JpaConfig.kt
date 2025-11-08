package com.advisor.api.app.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = [
    "com.advisor.api.iam.infrastructure",
    "com.advisor.api.subscription.infrastructure"
])
@EnableJpaRepositories(basePackages = [
    "com.advisor.api.iam.infrastructure",
    "com.advisor.api.subscription.infrastructure"
])
class JpaConfig
