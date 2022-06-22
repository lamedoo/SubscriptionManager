package com.lukakordzaia.core_network.repository.addsubscription

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.core_network.Constants
import com.lukakordzaia.core_network.ResultNetwork
import com.lukakordzaia.core_network.networkmodels.AddSubscriptionItemNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class DefaultAddSubscriptionRepository: AddSubscriptionRepository {
    override fun addSubscription(userId: String, subscription: AddSubscriptionItemNetwork): Flow<ResultNetwork<Boolean>> = flow {
        try {
            Firebase.firestore
                .collection(Constants.USERS_COLLECTION)
                .document(userId)
                .collection(Constants.SUBSCRIPTIONS_COLLECTION)
                .document(subscription.id)
                .set(subscription)
                .await()
            return@flow emit(ResultNetwork.Success(true))
        } catch (ex: Exception) {
            return@flow emit(ResultNetwork.Error(ex))
        }
    }
}