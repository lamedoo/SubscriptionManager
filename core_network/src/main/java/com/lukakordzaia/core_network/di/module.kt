package com.lukakordzaia.core_network.di

import com.lukakordzaia.core_network.repository.addsubscription.AddSubscriptionRepository
import com.lukakordzaia.core_network.repository.addsubscription.DefaultAddSubscriptionRepository
import com.lukakordzaia.core_network.repository.homerepository.DefaultHomeRepository
import com.lukakordzaia.core_network.repository.homerepository.HomeRepository
import com.lukakordzaia.core_network.repository.login.DefaultLoginRepository
import com.lukakordzaia.core_network.repository.login.LoginRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<LoginRepository> { DefaultLoginRepository() }
    single<HomeRepository> { DefaultHomeRepository() }
    single<AddSubscriptionRepository> { DefaultAddSubscriptionRepository() }
}