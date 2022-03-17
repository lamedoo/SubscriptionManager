package com.lukakordzaia.subscriptionmanager.domain.repository.addsubscription

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.subscriptionmanager.network.ResultNetwork
import com.lukakordzaia.subscriptionmanager.network.networkmodels.AddSubscriptionItemNetwork
import com.lukakordzaia.subscriptionmanager.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception

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