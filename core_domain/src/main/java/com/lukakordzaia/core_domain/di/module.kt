package com.lukakordzaia.core_domain.di

import com.lukakordzaia.core_domain.usecases.*
import org.koin.dsl.module

val useCaseModule = module {
    single { AddUserFirestoreUseCase(get()) }
    single { UserLoginUseCase(get()) }
    single { GetSubscriptionsUseCase(get()) }
    single { AddSubscriptionUseCase(get()) }
    single { DeleteSubscriptionUseCase(get()) }
}