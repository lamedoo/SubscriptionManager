package com.lukakordzaia.core_network.repository.login

import com.google.firebase.auth.FirebaseUser
import com.lukakordzaia.core_network.ResultNetwork
import com.lukakordzaia.subscriptionmanager.network.networkmodels.UserLoginRequestNetwork
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun userLogin(data: UserLoginRequestNetwork): Flow<ResultNetwork<Boolean>>
    suspend fun addUserFirestore(user: FirebaseUser): Flow<ResultNetwork<Boolean>>
}