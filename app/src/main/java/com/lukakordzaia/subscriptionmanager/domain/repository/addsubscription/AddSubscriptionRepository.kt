package com.lukakordzaia.subscriptionmanager.domain.repository.addsubscription

import com.lukakordzaia.subscriptionmanager.network.ResultNetwork
import com.lukakordzaia.subscriptionmanager.network.networkmodels.AddSubscriptionItemNetwork
import kotlinx.coroutines.flow.Flow

interface AddSubscriptionRepository {
    fun addSubscription(userId: String, subscription: AddSubscriptionItemNetwork): Flow<ResultNetwork<Boolean>>
}