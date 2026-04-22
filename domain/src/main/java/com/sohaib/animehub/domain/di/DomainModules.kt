package com.sohaib.animehub.domain.di

import com.sohaib.animehub.domain.useCases.GetAnimeListUseCase
import org.koin.dsl.lazyModule

val domainModule = lazyModule {
    factory { GetAnimeListUseCase(get()) }
}