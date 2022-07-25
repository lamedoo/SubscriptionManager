package com.lukakordzaia.core_network.repository.subscriptiondetails

import com.lukakordzaia.core_network.ResultNetwork
import kotlinx.coroutines.flow.Flow

interface SubscriptionDetailsRepository {
    fun deleteSubscription(userId: String, subscriptionId: String): Flow<ResultNetwork<Boolean>>
}