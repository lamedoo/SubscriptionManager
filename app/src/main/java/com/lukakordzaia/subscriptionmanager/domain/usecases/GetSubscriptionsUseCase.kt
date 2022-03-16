package com.lukakordzaia.subscriptionmanager.domain.usecases

import com.lukakordzaia.subscriptionmanager.base.BaseFlowUseCase
import com.lukakordzaia.subscriptionmanager.domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.subscriptionmanager.domain.repository.homerepository.HomeRepository
import com.lukakordzaia.subscriptionmanager.network.ResultDomain
import com.lukakordzaia.subscriptionmanager.network.networkmodels.SubscriptionItemNetwork
import com.lukakordzaia.subscriptionmanager.network.transformToDomain
import kotlinx.coroutines.flow.Flow

class GetSubscriptionsUseCase(
    private val repository: HomeRepository
): BaseFlowUseCase<String, List<SubscriptionItemNetwork>, List<SubscriptionItemDomain>>() {
    override suspend fun invoke(args: String?): Flow<ResultDomain<List<SubscriptionItemDomain>, String>> {
        return transformToDomain(repository.getUserSubscriptions(args!!)) { it.transformToDomain() }
    }
}