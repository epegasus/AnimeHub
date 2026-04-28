package com.sohaib.animehub.domain.di

import com.sohaib.animehub.domain.useCases.GetAnimeDetailsByIdUseCase
import com.sohaib.animehub.domain.useCases.GetAnimeListUseCase
import com.sohaib.animehub.domain.useCases.RefreshAnimeDetailsByIdUseCase
import com.sohaib.animehub.domain.useCases.RefreshAnimeListUseCase
import org.koin.dsl.lazyModule

val domainModule = lazyModule {
    factory { GetAnimeListUseCase(get()) }
    factory { GetAnimeDetailsByIdUseCase(get()) }

    factory { RefreshAnimeListUseCase(get()) }
    factory { RefreshAnimeDetailsByIdUseCase(get()) }
}