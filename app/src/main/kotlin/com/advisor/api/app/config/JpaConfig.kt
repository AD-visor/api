package com.advisor.api.app.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = [
    "com.advisor.iam.infrastructure",
])
@EnableJpaRepositories(basePackages = [
    "com.advisor.iam.infrastructure.auth",
])
class JpaConfig
