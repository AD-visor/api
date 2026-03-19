package com.advisor.api.iam.port.outbound.auth

interface AuthTokenPort {
    fun generateAccessToken(subject: String, claims: Map<String, Any>): String
    fun generateRefreshToken(subject: String, claims: Map<String, Any>): String
    fun validateAccessToken(token: String): Boolean
    fun validateRefreshToken(token: String): Boolean
    fun getAccessClaims(token: String): Map<String, Any>
    fun getRefreshClaims(token: String): Map<String, Any>
    fun getAccessSubject(token: String): String
    fun getRefreshSubject(token: String): String
}
