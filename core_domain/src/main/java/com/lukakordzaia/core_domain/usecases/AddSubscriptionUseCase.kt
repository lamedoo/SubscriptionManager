package com.lukakordzaia.core_domain.usecases

import com.lukakordzaia.core_domain.BaseFlowUseCase
import com.lukakordzaia.core_domain.ResultDomain
import com.lukakordzaia.core_network.networkmodels.AddSubscriptionItemNetwork
import kotlinx.coroutines.flow.Flow

class AddSubscriptionUseCase(
    private val repository: com.lukakordzaia.core_network.repository.addsubscription.AddSubscriptionRepository
) : BaseFlowUseCase<AddSubscriptionUseCase.Params, Boolean, Boolean>() {
    override suspend fun invoke(args: Params?): Flow<ResultDomain<Boolean, String>> {
        return transformToDomain(repository.addSubscription(
            args!!.userId,
            AddSubscriptionItemNetwork(
                args.subscription.id,
                args.subscription.name,
                args.subscription.plan,
                args.subscription.color,
                args.subscription.amount,
                args.subscription.currency,
                args.subscription.periodType,
                args.subscription.date,
                args.subscription.subscriptionType,
                args.subscription.updateDate
            )
        )) { it }
    }

    data class Params(
        val userId: String,
        val subscription: AddSubscriptionItem
    ) {
        data class AddSubscriptionItem(
            val id: String,
            val name: String,
            val plan: String?,
            val color: Int?,
            val amount: Double,
            val currency: String,
            val periodType: Int,
            val date: Long?,
            val subscriptionType: Int,
            val updateDate: Long
        )
    }
}