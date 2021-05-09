package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.picPayModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(picPayModules)
        }
    }
}