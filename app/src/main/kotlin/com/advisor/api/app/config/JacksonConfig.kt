package com.advisor.api.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import tools.jackson.databind.DeserializationFeature
import tools.jackson.databind.cfg.DateTimeFeature
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.KotlinModule

@Configuration
class JacksonConfig {

    @Bean
    @Primary
    fun objectMapper(): JsonMapper {
        return JsonMapper.builder()
            // Kotlin Module: Kotlin의 Data Class 및 Non-nullable 필드 지원
            .addModule(KotlinModule.Builder().build())

            // 직렬화 설정: 날짜를 타임스탬프로 쓰지 않고 ISO-8601 형식으로 쓰도록 설정
            .disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)

            // 역직렬화 설정: JSON에는 있지만 객체에는 없는 필드가 있어도 에러 내지 않음 (호환성)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

            .build()
    }
}
