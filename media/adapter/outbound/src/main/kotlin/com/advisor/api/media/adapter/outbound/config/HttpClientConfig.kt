package com.advisor.api.media.adapter.outbound.config

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HttpClientConfig {
    @Bean
    fun httpClient(): CloseableHttpClient = HttpClients.createDefault()
}
