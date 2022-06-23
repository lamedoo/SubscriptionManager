package com.lukakordzaia.core_domain.usecases

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.lukakordzaia.core_domain.BaseFlowUseCase
import com.lukakordzaia.core_domain.ResultDomain
import com.lukakordzaia.core_network.repository.login.LoginRepository
import com.lukakordzaia.subscriptionmanager.network.networkmodels.UserLoginRequestNetwork
import kotlinx.coroutines.flow.Flow

class UserLoginUseCase(
    private val repository: LoginRepository
) : BaseFlowUseCase<UserLoginUseCase.Params, Boolean, Boolean>() {
    override suspend fun invoke(args: Params?): Flow<ResultDomain<Boolean, String>> {
        return transformToDomain(repository.userLogin(UserLoginRequestNetwork(args!!.auth, args.credential))) { it }
    }

    data class Params(
        val auth: FirebaseAuth,
        val credential: AuthCredential
    )
}