package com.advisor.api.app.config.security

import com.advisor.api.app.config.security.SecurityPathFilter.AUTH_PATHS
import com.advisor.api.app.config.security.SecurityPathFilter.PUBLIC_PATHS
import com.advisor.api.app.config.security.jwt.JwtAuthenticationFilter
import com.advisor.api.iam.port.outbound.auth.AuthTokenPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authTokenPort: AuthTokenPort
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // 기본 설정
        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .cors { }

        // 경로별 권한 설정
        http.authorizeHttpRequests {
            it.requestMatchers(*PUBLIC_PATHS).permitAll()
            it.requestMatchers(*AUTH_PATHS).permitAll()
            it.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            it.anyRequest().authenticated()
        }

        // OAuth2 로그인 설정
        /*
        http.oauth2Login {
            it.userInfoEndpoint {
            }
            //it.successHandler()
        }

         */

        // JWT 인증 필터
        http.addFilterBefore(JwtAuthenticationFilter(authTokenPort), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
