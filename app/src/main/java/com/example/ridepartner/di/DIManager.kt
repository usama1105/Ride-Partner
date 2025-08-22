package com.example.ridepartner.di

import android.content.Context
import com.example.network.di.networkModule
import com.ridepartner.login.di.loginModule
import com.ridepartner.login.di.loginRepoModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.math.log

class DIManager private constructor(private val application: Context) {

    companion object {
        fun initialize(application: Context) {
            DIManager(application)
        }
    }

    init {
        startKoin {
            androidLogger()
            androidContext(application)
            modules(getModules())
        }
    }

    private val repoList
        get() = listOf(
            loginRepoModule,
            networkModule,
        )


    private val viewModelList
        get() = listOf(
            loginModule
        )

    private val moduleList
        get() = listOf(
            repoList,
            viewModelList,
        )

    private fun getModules(): List<Module> {
        return moduleList.flatten()
    }

}