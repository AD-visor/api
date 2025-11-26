package com.advisor.api.app.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = [
    "com.advisor.api.iam.adapter.outbound",
    "com.advisor.api.subscription.adapter.outbound",
    "com.advisor.api.conversation.adapter.outbound",
    "com.advisor.api.payment.adapter.outbound"
])
@EnableJpaRepositories(basePackages = [
    "com.advisor.api.iam.adapter.outbound",
    "com.advisor.api.subscription.adapter.outbound",
    "com.advisor.api.conversation.adapter.outbound",
    "com.advisor.api.payment.adapter.outbound"
])
class JpaConfig
