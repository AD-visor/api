package com.advisor.api.media.adapter.outbound.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.JdkClientHttpRequestFactory
import org.springframework.web.client.RestClient
import java.time.Duration

@Configuration
class RestClientConfig {
    @Bean
    fun restClient(): RestClient {
        val factory = JdkClientHttpRequestFactory().apply {
            setReadTimeout(Duration.ofSeconds(15))
        }

        return RestClient.builder()
            .requestFactory(factory)
            .defaultHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
            .requestInterceptor { request, body, execution ->
                execution.execute(request, body)
            }
            .build()
    }
}
