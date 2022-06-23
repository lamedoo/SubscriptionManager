package com.lukakordzaia.core_domain.di

import com.lukakordzaia.core_domain.usecases.AddSubscriptionUseCase
import com.lukakordzaia.core_domain.usecases.AddUserFirestoreUseCase
import com.lukakordzaia.core_domain.usecases.GetSubscriptionsUseCase
import com.lukakordzaia.core_domain.usecases.UserLoginUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { AddUserFirestoreUseCase(get()) }
    single { UserLoginUseCase(get()) }
    single { GetSubscriptionsUseCase(get()) }
    single { AddSubscriptionUseCase(get()) }
}