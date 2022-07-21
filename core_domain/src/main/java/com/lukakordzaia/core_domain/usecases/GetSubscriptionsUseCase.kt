package com.lukakordzaia.core_domain.usecases

import com.lukakordzaia.core_domain.BaseFlowUseCase
import com.lukakordzaia.core_domain.ResultDomain
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.core_domain.transformToDomain
import com.lukakordzaia.core_network.repository.homerepository.HomeRepository
import com.lukakordzaia.subscriptionmanager.network.networkmodels.SubscriptionItemNetwork
import kotlinx.coroutines.flow.Flow

class GetSubscriptionsUseCase(
    private val repository: HomeRepository
): BaseFlowUseCase<String, List<SubscriptionItemNetwork>, List<SubscriptionItemDomain>>() {
    override suspend fun invoke(args: String?): Flow<ResultDomain<List<SubscriptionItemDomain>, String>> {
        return transformToDomain(repository.getUserSubscriptions(args!!)) { data ->
            data.transformToDomain()
                .sortedBy { it.subscriptionType }
        }
    }
}