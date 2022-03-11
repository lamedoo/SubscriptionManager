package com.lukakordzaia.subscriptionmanager.di

import com.lukakordzaia.subscriptionmanager.ui.home.HomeVM
import com.lukakordzaia.subscriptionmanager.helpers.Navigation
import com.lukakordzaia.subscriptionmanager.ui.addsubscription.AddSubscriptionVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeVM() }
    viewModel { AddSubscriptionVM() }
}

val repositoryModule = module {

}

val generalModule = module {
    single { Navigation() }
}