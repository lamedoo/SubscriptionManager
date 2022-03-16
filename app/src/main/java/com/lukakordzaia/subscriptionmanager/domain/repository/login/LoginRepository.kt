package com.lukakordzaia.subscriptionmanager.domain.repository.login

import com.google.firebase.auth.FirebaseUser
import com.lukakordzaia.subscriptionmanager.network.ResultNetwork
import com.lukakordzaia.subscriptionmanager.network.networkmodels.UserLoginRequestNetwork
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun userLogin(data: UserLoginRequestNetwork): Flow<ResultNetwork<Boolean>>
    suspend fun addUserFirestore(user: FirebaseUser): Flow<ResultNetwork<Boolean>>
}