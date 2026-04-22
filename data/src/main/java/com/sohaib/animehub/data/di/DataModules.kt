package com.sohaib.animehub.data.di

import com.sohaib.animehub.data.dataSources.remote.AnimeRemoteDataSource
import com.sohaib.animehub.data.repositories.AnimeRepositoryImpl
import com.sohaib.animehub.domain.repositories.AnimeRepository
import org.koin.dsl.lazyModule

val dataSourceModule = lazyModule {
    single { AnimeRemoteDataSource(get()) }
}

val repositoriesModule = lazyModule {
    single<AnimeRepository> { AnimeRepositoryImpl(get(), get()) }
}