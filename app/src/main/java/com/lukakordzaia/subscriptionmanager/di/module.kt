package com.lukakordzaia.subscriptionmanager.di

import com.lukakordzaia.subscriptionmanager.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
}

val repositoryModule = module {

}