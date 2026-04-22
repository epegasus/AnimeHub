package com.sohaib.animehub.core.network

import com.sohaib.animehub.core.network.apis.AnimeApiService
import com.sohaib.animehub.core.network.client.OkHttpClients
import com.sohaib.animehub.core.network.interceptors.AuthInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://kitsu.io/api/edge/"

    private val authInterceptor by lazy { AuthInterceptor { null } }

    private val okHttpClient by lazy { OkHttpClients().getHttpClient(authInterceptor = authInterceptor, isDebug = true) }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val animeApiService: AnimeApiService by lazy { retrofit.create(AnimeApiService::class.java) }
}