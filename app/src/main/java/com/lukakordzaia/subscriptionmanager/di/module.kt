package com.lukakordzaia.subscriptionmanager.di

import com.lukakordzaia.core.helpers.ResourceProvider
import com.lukakordzaia.feature_add_subscription.AddSubscriptionVM
import com.lukakordzaia.feature_subscription_details.SubscriptionDetailsVM
import com.lukakordzaia.feature_subscriptions.SubscriptionsVM
import com.lukakordzaia.featurelogin.ui.LoginVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SubscriptionsVM(get(), get(), get()) }
    viewModel { AddSubscriptionVM(get()) }
    viewModel { LoginVM(get(), get()) }
    viewModel { SubscriptionDetailsVM(get()) }
}

val generalModule = module {
    single { ResourceProvider(get()) }
}