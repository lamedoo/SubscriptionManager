package com.lukakordzaia.core_network.repository.homerepository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.core_network.Constants
import com.lukakordzaia.core_network.ResultNetwork
import com.lukakordzaia.subscriptionmanager.network.networkmodels.SubscriptionItemNetwork
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DefaultHomeRepository: HomeRepository {
    override fun getUserSubscriptions(userId: String): Flow<ResultNetwork<List<SubscriptionItemNetwork>>> = callbackFlow {
        val subscriptionRef = Firebase.firestore
            .collection(Constants.USERS_COLLECTION)
            .document(userId)
            .collection(Constants.SUBSCRIPTIONS_COLLECTION)

        val snapshot = subscriptionRef.addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val subscriptions = snapshot.toObjects(SubscriptionItemNetwork::class.java)
                ResultNetwork.Success(subscriptions)
            } else {
                ResultNetwork.Error(e.toString())
            }
            trySend(response)
        }
        awaitClose {
            snapshot.remove()
        }
    }
}