package com.lukakordzaia.subscriptionmanager.di

import com.lukakordzaia.subscriptionmanager.ui.home.HomeViewModel
import com.lukakordzaia.subscriptionmanager.helpers.Navigation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
}

val repositoryModule = module {

}

val generalModule = module {
    single { Navigation() }
}