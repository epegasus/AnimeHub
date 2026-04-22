package com.sohaib.animehub.core.network.client

import com.sohaib.animehub.core.network.interceptors.AuthInterceptor
import com.sohaib.animehub.core.network.interceptors.LoggingInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpClients {

    fun getHttpClient(
        authInterceptor: AuthInterceptor = AuthInterceptor(),
        isDebug: Boolean = true,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(LoggingInterceptor.create(isDebug))
            .build()
    }
}