package com.sohaib.animehub

import android.app.Application
import com.sohaib.animehub.di.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.lazyModules

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        val list = KoinModules().list
        startKoin {
            androidContext(this@App)
            lazyModules(list)
        }
    }
}