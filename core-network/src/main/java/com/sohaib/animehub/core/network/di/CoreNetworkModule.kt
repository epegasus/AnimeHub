package com.sohaib.animehub.core.network.di

import com.sohaib.animehub.core.network.RetrofitClient
import org.koin.dsl.lazyModule

val coreNetworkModule = lazyModule {
    single { RetrofitClient.animeApiService }
}