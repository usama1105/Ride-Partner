package com.ridepartner.login.di

import com.ridepartner.login.data.repo.LoginRepositoryImpl
import com.ridepartner.login.domain.repo.LoginRepo
import org.koin.dsl.module

val loginRepoModule = module {
    single<LoginRepo> { LoginRepositoryImpl() }
}