package com.lukakordzaia.subscriptionmanager.domain.repository.login

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.subscriptionmanager.network.ResultNetwork
import com.lukakordzaia.subscriptionmanager.network.networkmodels.UserLoginRequestNetwork
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class DefaultLoginRepository: LoginRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun userLogin(data: UserLoginRequestNetwork): Flow<ResultNetwork<Boolean>> = callbackFlow {
        val listener = OnCompleteListener<AuthResult> { task ->
            if (task.isSuccessful) {
                trySend(ResultNetwork.Success(true)).isSuccess
            } else {
                trySend(ResultNetwork.Success(false)).isFailure
            }
        }

        data.auth.signInWithCredential(data.credential)
            .addOnCompleteListener(listener)

        awaitClose {}
    }

    override suspend fun addUserFirestore(user: FirebaseUser): Flow<ResultNetwork<Boolean>> = flow {
        try {
            Firebase.firestore
                .collection("users")
                .document(user.uid)
                .set(
                    mapOf(
                        "email" to user.email
                    )
                )
                .await()
            return@flow emit(ResultNetwork.Success(true))
        } catch (ex: Exception) {
            Log.d("adsasdasdsa", ex.toString())
            return@flow emit(ResultNetwork.Error(false))
        }
    }
}