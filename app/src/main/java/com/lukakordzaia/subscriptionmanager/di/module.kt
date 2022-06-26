package com.lukakordzaia.subscriptionmanager.di

import com.lukakordzaia.feature_add_subscription.AddSubscriptionVM
import com.lukakordzaia.feature_home.HomeVM
import com.lukakordzaia.feature_subscription_details.SubscriptionDetailsVM
import com.lukakordzaia.featurelogin.ui.LoginVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeVM(get()) }
    viewModel { AddSubscriptionVM(get()) }
    viewModel { LoginVM(get(), get()) }
    viewModel { SubscriptionDetailsVM() }
}

val generalModule = module {}