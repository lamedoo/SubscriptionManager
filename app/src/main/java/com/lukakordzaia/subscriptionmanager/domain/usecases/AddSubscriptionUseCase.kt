package com.lukakordzaia.subscriptionmanager.domain.usecases

import com.lukakordzaia.subscriptionmanager.base.BaseFlowUseCase
import com.lukakordzaia.subscriptionmanager.domain.repository.addsubscription.AddSubscriptionRepository
import com.lukakordzaia.subscriptionmanager.domain.repository.addsubscription.DefaultAddSubscriptionRepository
import com.lukakordzaia.subscriptionmanager.network.ResultDomain
import com.lukakordzaia.subscriptionmanager.network.networkmodels.AddSubscriptionItemNetwork
import kotlinx.coroutines.flow.Flow

class AddSubscriptionUseCase(
    private val repository: AddSubscriptionRepository
) : BaseFlowUseCase<AddSubscriptionUseCase.Params, Boolean, Boolean>() {
    override suspend fun invoke(args: Params?): Flow<ResultDomain<Boolean, String>> {
        return transformToDomain(repository.addSubscription(args!!.userId, args.subscription)) { it }
    }

    data class Params(
        val userId: String,
        val subscription: AddSubscriptionItemNetwork
    )
}