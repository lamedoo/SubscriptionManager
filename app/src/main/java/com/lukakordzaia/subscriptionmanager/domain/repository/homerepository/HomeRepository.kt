package com.lukakordzaia.subscriptionmanager.domain.repository.homerepository

import com.lukakordzaia.subscriptionmanager.network.ResultNetwork
import com.lukakordzaia.subscriptionmanager.network.networkmodels.SubscriptionItemNetwork
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getUserSubscriptions(userId: String): Flow<ResultNetwork<List<SubscriptionItemNetwork>>>
}