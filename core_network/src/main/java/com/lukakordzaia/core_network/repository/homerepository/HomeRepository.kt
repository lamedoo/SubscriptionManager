package com.lukakordzaia.core_network.repository.homerepository

import com.lukakordzaia.core_network.ResultNetwork
import com.lukakordzaia.subscriptionmanager.network.networkmodels.SubscriptionItemNetwork
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getUserSubscriptions(userId: String): Flow<ResultNetwork<List<SubscriptionItemNetwork>>>
}