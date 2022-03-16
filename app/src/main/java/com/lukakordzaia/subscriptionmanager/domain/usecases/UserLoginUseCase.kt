package com.lukakordzaia.subscriptionmanager.domain.usecases

import com.lukakordzaia.subscriptionmanager.base.BaseFlowUseCase
import com.lukakordzaia.subscriptionmanager.domain.repository.login.LoginRepository
import com.lukakordzaia.subscriptionmanager.network.ResultDomain
import com.lukakordzaia.subscriptionmanager.network.networkmodels.UserLoginRequestNetwork
import kotlinx.coroutines.flow.Flow

class UserLoginUseCase(
    private val repository: LoginRepository
) : BaseFlowUseCase<UserLoginRequestNetwork, Boolean, Boolean>() {
    override suspend fun invoke(args: UserLoginRequestNetwork?): Flow<ResultDomain<Boolean, String>> {
        return transformToDomain(repository.userLogin(args!!)) { it }
    }
}