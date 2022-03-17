package com.lukakordzaia.subscriptionmanager.di

import com.lukakordzaia.subscriptionmanager.domain.repository.addsubscription.AddSubscriptionRepository
import com.lukakordzaia.subscriptionmanager.domain.repository.addsubscription.DefaultAddSubscriptionRepository
import com.lukakordzaia.subscriptionmanager.domain.repository.homerepository.DefaultHomeRepository
import com.lukakordzaia.subscriptionmanager.domain.repository.homerepository.HomeRepository
import com.lukakordzaia.subscriptionmanager.domain.repository.login.DefaultLoginRepository
import com.lukakordzaia.subscriptionmanager.domain.repository.login.LoginRepository
import com.lukakordzaia.subscriptionmanager.domain.usecases.AddSubscriptionUseCase
import com.lukakordzaia.subscriptionmanager.domain.usecases.AddUserFirestoreUseCase
import com.lukakordzaia.subscriptionmanager.domain.usecases.GetSubscriptionsUseCase
import com.lukakordzaia.subscriptionmanager.domain.usecases.UserLoginUseCase
import com.lukakordzaia.subscriptionmanager.ui.main.home.HomeVM
import com.lukakordzaia.subscriptionmanager.helpers.Navigation
import com.lukakordzaia.subscriptionmanager.ui.login.LoginVM
import com.lukakordzaia.subscriptionmanager.ui.main.addsubscription.AddSubscriptionVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeVM(get()) }
    viewModel { AddSubscriptionVM(get()) }
    viewModel { LoginVM(get(), get()) }
}

val repositoryModule = module {
    single<LoginRepository> { DefaultLoginRepository() }
    single<HomeRepository> { DefaultHomeRepository() }
    single<AddSubscriptionRepository> { DefaultAddSubscriptionRepository() }
}

val generalModule = module {
    single { Navigation() }
}

val useCaseModule = module {
    single { AddUserFirestoreUseCase(get()) }
    single { UserLoginUseCase(get()) }
    single { GetSubscriptionsUseCase(get()) }
    single { AddSubscriptionUseCase(get()) }
}