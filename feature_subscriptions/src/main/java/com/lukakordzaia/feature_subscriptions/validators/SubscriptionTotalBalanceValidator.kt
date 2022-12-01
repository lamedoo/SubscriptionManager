package com.lukakordzaia.feature_subscriptions.validators

import com.lukakordzaia.core_domain.BaseUseCase
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain

class SubscriptionTotalBalanceValidator : BaseUseCase<List<SubscriptionItemDomain>, Double> {
    override suspend fun invoke(args: List<SubscriptionItemDomain>?): Double {
        var totalBalance = 0.0

        args?.let { subscription ->
            subscription.forEach {
                totalBalance += it.amount
            }
        }

        return totalBalance
    }
}