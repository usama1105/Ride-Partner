package com.ridepartner.shared.di

import com.ridepartner.dashboard.presentation.ui.test.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val DashboardModule = module {
    viewModel { DashboardViewModel() }
}