package com.example.ridepartner.di

import android.app.Application
import com.ridepartner.login.di.loginModule
import com.ridepartner.shared.di.DashboardModule
import com.ridepartner.shared.di.SharedModule


import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class APP : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@APP)
            modules(
                SharedModule,
                loginModule,
                DashboardModule
            )
        }
    }
}