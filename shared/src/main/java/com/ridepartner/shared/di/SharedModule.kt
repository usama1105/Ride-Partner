package com.ridepartner.shared.di

import com.ridepartner.shared.presentation.ui.test.TestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val SharedModule = module {
    viewModel { TestViewModel() }
}