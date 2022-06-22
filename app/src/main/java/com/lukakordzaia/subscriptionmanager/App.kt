package com.lukakordzaia.subscriptionmanager

import android.app.Application
import com.lukakordzaia.core_domain.di.useCaseModule
import com.lukakordzaia.core_network.di.repositoryModule
import com.lukakordzaia.subscriptionmanager.di.generalModule
import com.lukakordzaia.subscriptionmanager.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
            startKoin {
                androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
                androidContext(this@App)
                modules(listOf(viewModelModule, repositoryModule, generalModule, useCaseModule))
        }
    }
}