package com.lukakordzaia.core_network.repository.addsubscription

import com.lukakordzaia.core_network.ResultNetwork
import com.lukakordzaia.core_network.networkmodels.AddSubscriptionItemNetwork
import kotlinx.coroutines.flow.Flow

interface AddSubscriptionRepository {
    fun addSubscription(userId: String, subscription: AddSubscriptionItemNetwork): Flow<ResultNetwork<Boolean>>
}