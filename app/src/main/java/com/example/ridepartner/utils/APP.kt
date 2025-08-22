package com.example.ridepartner.utils

import android.app.Application
import com.example.network.utils.NetworkBuilder
import com.example.ridepartner.BuildConfig
import com.example.ridepartner.di.DIManager

class APP: Application() {
    override fun onCreate() {
        super.onCreate()
        DIManager.initialize(this)

        NetworkBuilder.init(
            isMocked = false,
            isDebug = BuildConfig.DEBUG,
            apiVersion = "api/v1/",
            isTestServer = false
        )
    }
}