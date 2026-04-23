package com.sohaib.animehub.feature.splash.di

import com.sohaib.animehub.feature.splash.viewModel.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val splashFeatureModule = lazyModule {
    viewModel { SplashViewModel() }
}