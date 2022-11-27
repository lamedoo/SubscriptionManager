package com.lukakordzaia.core_domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.lukakordzaia.core_domain.BaseFlowUseCase
import com.lukakordzaia.core_domain.ResultDomain
import com.lukakordzaia.core_network.repository.login.LoginRepository
import kotlinx.coroutines.flow.Flow

class AddUserFirestoreUseCase(
    private val repository: LoginRepository
) : BaseFlowUseCase<FirebaseUser, Boolean, Boolean>() {
    override suspend fun invoke(args: FirebaseUser?): Flow<ResultDomain<Boolean, String>> {
        return transformToDomain(repository.addUserFirestore(args!!)) { it }
    }
}