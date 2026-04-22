package com.sohaib.animehub.core.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Adds bearer token when a token is available.
 * Keep this interceptor in core-network so data/domain stay transport-agnostic.
 */
class AuthInterceptor(
    private val tokenProvider: () -> String? = { null },
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenProvider().orEmpty()

        if (token.isBlank()) {
            return chain.proceed(originalRequest)
        }

        val authorizedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(authorizedRequest)
    }
}