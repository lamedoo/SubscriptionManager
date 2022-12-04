package com.lukakordzaia.core_network.di

import com.lukakordzaia.core_network.api.ApiCallWrapper
import com.lukakordzaia.core_network.api.CurrencyService
import com.lukakordzaia.core_network.repository.addsubscription.AddSubscriptionRepository
import com.lukakordzaia.core_network.repository.addsubscription.DefaultAddSubscriptionRepository
import com.lukakordzaia.core_network.repository.currency.CurrencyRepository
import com.lukakordzaia.core_network.repository.currency.DefaultCurrencyRepository
import com.lukakordzaia.core_network.repository.homerepository.DefaultHomeRepository
import com.lukakordzaia.core_network.repository.homerepository.HomeRepository
import com.lukakordzaia.core_network.repository.login.DefaultLoginRepository
import com.lukakordzaia.core_network.repository.login.LoginRepository
import com.lukakordzaia.core_network.repository.subscriptiondetails.DefaultSubscriptionDetailsRepository
import com.lukakordzaia.core_network.repository.subscriptiondetails.SubscriptionDetailsRepository
import com.lukakordzaia.core_network.retrofit.RetrofitCurrencyBuilder
import org.koin.dsl.module

val repositoryModule = module {
    single<LoginRepository> { DefaultLoginRepository() }
    single<HomeRepository> { DefaultHomeRepository() }
    single<AddSubscriptionRepository> { DefaultAddSubscriptionRepository() }
    single<SubscriptionDetailsRepository> { DefaultSubscriptionDetailsRepository() }
    single<CurrencyRepository> { DefaultCurrencyRepository(get(), get()) }
    single { ApiCallWrapper }
    single { RetrofitCurrencyBuilder() }
    single { get<RetrofitCurrencyBuilder>().getRetrofitInstance().create(CurrencyService::class.java) }
}