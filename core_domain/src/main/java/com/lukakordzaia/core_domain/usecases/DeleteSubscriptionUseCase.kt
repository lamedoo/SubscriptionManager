package com.lukakordzaia.core_domain.usecases

import com.lukakordzaia.core_domain.BaseFlowUseCase
import com.lukakordzaia.core_domain.ResultDomain
import com.lukakordzaia.core_network.repository.subscriptiondetails.SubscriptionDetailsRepository
import kotlinx.coroutines.flow.Flow

class DeleteSubscriptionUseCase(
    private val repository: SubscriptionDetailsRepository
) : BaseFlowUseCase<DeleteSubscriptionUseCase.Params, Boolean, Boolean>() {
    override suspend fun invoke(args: Params?): Flow<ResultDomain<Boolean, String>> {
        return transformToDomain(repository.deleteSubscription(
            args!!.userId,
            args.subscriptionId
        )) { it }
    }

    data class Params(
        val userId: String,
        val subscriptionId: String
    )
}