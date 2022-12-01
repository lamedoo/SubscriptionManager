package com.lukakordzaia.feature_subscriptions.di

import com.lukakordzaia.feature_subscriptions.validators.SortSubscriptionsValidator
import com.lukakordzaia.feature_subscriptions.validators.SubscriptionTotalBalanceValidator
import org.koin.dsl.module

val addSubscriptionModule = module {
    single { SortSubscriptionsValidator() }
    single { SubscriptionTotalBalanceValidator() }
}