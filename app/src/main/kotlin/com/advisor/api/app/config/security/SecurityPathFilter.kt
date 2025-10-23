package com.advisor.api.app.config.security

import org.springframework.util.AntPathMatcher

object SecurityPathFilter {
    private val pathMatcher = AntPathMatcher()

    val PUBLIC_PATHS = arrayOf(
        "/"
    )

    val AUTH_PATHS = arrayOf(
        "/"
    )

    val PROTECTED_PATHS = arrayOf(
        "/"
    )

    val INTERNAL_PATHS = arrayOf(
        "/"
    )

    fun isPublicPath(path: String) = matches(PUBLIC_PATHS, path)
    fun isAuthPath(path: String) = matches(AUTH_PATHS, path)
    fun isProtectedPath(path: String) = matches(PROTECTED_PATHS, path)
    fun isInternalPath(path: String) = matches(INTERNAL_PATHS, path)

    private fun matches(patterns: Array<String>, path: String): Boolean =
        patterns.any { pathMatcher.match(it, path) }
}
