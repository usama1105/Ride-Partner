package com.example.ridepartner.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

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

/*    private val repoModuleList
        get() = listOf(
//            searchRecipeRepoModule,
//            makeRecipeRepoModule,
//            detailRecipeRepoModule,
//            modifyRecipeRepoModule,
//            databaseRepoModule

        )
    private val retrofitModuleList
        get() = listOf(
//            searchRecipeRetrofitModule,
//            makeRecipeRetrofitModuleMake,
//            detailRecipeRetrofitModuleMake,
//            modifyRecipeRetrofitModuleMake
        )

    private val viewModelModuleList
        get() = listOf(
//            searchRecipeViewModelModule,
//            makeRecipeViewModelModule,
//            detailRecipeViewModelModule,
//            modifyRecipeViewModelModule

        )

    private val dbModuleList
        get() = listOf(
          //  databaseModule
        )*/

    private val moduleList
        get() = listOf(
            viewModelModuleList,
            repoModuleList,
            retrofitModuleList,
            dbModuleList
        )

    private fun getModules(): List<Module> {
        return moduleList.flatten()
    }
}