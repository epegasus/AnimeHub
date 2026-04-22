package com.sohaib.animehub.core.network.interceptors

import okhttp3.logging.HttpLoggingInterceptor

object LoggingInterceptor {

    fun create(isDebug: Boolean = true): HttpLoggingInterceptor {
        val level = when (isDebug) {
            true -> HttpLoggingInterceptor.Level.BODY
            false -> HttpLoggingInterceptor.Level.NONE
        }

        return HttpLoggingInterceptor().apply { this.level = level }
    }
}