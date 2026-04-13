package com.advisor.api.iam.adapter.outbound.auth.jwt

import com.advisor.api.common.exception.CustomException
import com.advisor.api.iam.adapter.outbound.auth.AuthInfrastructureExceptionCode
import com.advisor.api.iam.domain.auth.vo.JwtTokenType
import com.advisor.api.iam.port.outbound.auth.AuthTokenPort
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    private val props: JwtProperties
) : AuthTokenPort {
    private val accessKey: SecretKey = Keys.hmacShaKeyFor(props.access.secret.toByteArray())
    private val refreshKey: SecretKey = Keys.hmacShaKeyFor(props.refresh.secret.toByteArray())

    override fun generateAccessToken(subject: String, claims: Map<String, Any>): String =
        generateToken(subject, claims, JwtTokenType.ACCESS)

    override fun generateRefreshToken(subject: String, claims: Map<String, Any>): String =
        generateToken(subject, claims, JwtTokenType.REFRESH)

    override fun validateAccessToken(token: String): Boolean =
        validateToken(token, JwtTokenType.ACCESS)

    override fun validateRefreshToken(token: String): Boolean =
        validateToken(token, JwtTokenType.REFRESH)

    override fun getAccessClaims(token: String): Map<String, Any> =
        getClaims(token, JwtTokenType.ACCESS)

    override fun getRefreshClaims(token: String): Map<String, Any> =
        getClaims(token, JwtTokenType.REFRESH)

    override fun getAccessSubject(token: String): String =
        getSubject(token, JwtTokenType.ACCESS)

    override fun getRefreshSubject(token: String): String =
        getSubject(token, JwtTokenType.REFRESH)

    private fun getSubject(token: String, jwtTokenType: JwtTokenType): String =
        parse(token, getSecretKey(jwtTokenType)).payload.subject

    private fun getClaims(token: String, jwtTokenType: JwtTokenType): Map<String, Any> =
        parse(token, getSecretKey(jwtTokenType)).payload

    private fun validateToken(token: String, jwtTokenType: JwtTokenType): Boolean =
        validate(token, getSecretKey(jwtTokenType))

    private fun generateToken(
        subject: String,
        claims: Map<String, Any>,
        jwtTokenType: JwtTokenType
    ): String {
        val now = Date()
        val expiration = Date(now.time + getExpiration(jwtTokenType) * 1000)
        val secretKey = getSecretKey(jwtTokenType)

        return Jwts.builder()
            .subject(subject)
            .issuedAt(now)
            .expiration(expiration)
            .signWith(secretKey)
            .claims(claims)
            .compact()
    }

    private fun validate(token: String, key: SecretKey): Boolean = try {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)

        claims.payload.expiration.after(Date())
    } catch (e: ExpiredJwtException) {
        throw CustomException(
            AuthInfrastructureExceptionCode.AUTH_TOKEN_EXPIRED,
            "[Auth] 토큰이 만료되었습니다."
        )
    } catch (e: MalformedJwtException) {
        throw CustomException(
            AuthInfrastructureExceptionCode.AUTH_MALFORMED_TOKEN,
            "[Auth] 지원하지 않는 토큰 형식입니다."
        )
    } catch (e: SignatureException) {
        throw CustomException(
            AuthInfrastructureExceptionCode.AUTH_TOKEN_INVALID_SIGNATURE,
            "[Auth] 토큰 서명이 유효하지 않습니다."
        )
    } catch (e: IllegalArgumentException) {
        throw CustomException(
            AuthInfrastructureExceptionCode.AUTH_TOKEN_EMPTY,
            "[Auth] 토큰이 비어있습니다."
        )
    } catch (e: Exception) {
        throw CustomException(
            AuthInfrastructureExceptionCode.AUTH_UNEXPECTED_TOKEN_ERROR,
            "[Auth] 토큰 처리 중 알 수 없는 오류가 발생했습니다."
        )
    }

    private fun parse(token: String, key: SecretKey) =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)

    private fun getExpiration(jwtTokenType: JwtTokenType): Long {
        return when (jwtTokenType) {
            JwtTokenType.ACCESS -> props.access.expiration
            JwtTokenType.REFRESH -> props.refresh.expiration
        }
    }

    private fun getSecretKey(jwtTokenType: JwtTokenType): SecretKey {
        return when (jwtTokenType) {
            JwtTokenType.ACCESS -> accessKey
            JwtTokenType.REFRESH -> refreshKey
        }
    }
}
