package com.sohaib.animehub.core.common.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.lazyModule

val coreCommonModule = lazyModule {
    single { Dispatchers.IO }
    single { Dispatchers.Default }
}