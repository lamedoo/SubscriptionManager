package com.lukakordzaia.subscriptionmanager.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.lukakordzaia.subscriptionmanager.base.BaseFlowUseCase
import com.lukakordzaia.subscriptionmanager.base.BaseUseCase
import com.lukakordzaia.subscriptionmanager.domain.repository.login.LoginRepository
import com.lukakordzaia.subscriptionmanager.network.ResultDomain
import kotlinx.coroutines.flow.Flow

class AddUserFirestoreUseCase(
    private val repository: LoginRepository
) : BaseFlowUseCase<FirebaseUser, Boolean, Boolean>() {
    override suspend fun invoke(args: FirebaseUser?): Flow<ResultDomain<Boolean, String>> {
        return transformToDomain(repository.addUserFirestore(args!!)) { it }
    }
}