package com.lukakordzaia.subscriptionmanager.network

import com.lukakordzaia.subscriptionmanager.domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.subscriptionmanager.network.networkmodels.SubscriptionItemNetwork
import com.lukakordzaia.subscriptionmanager.utils.Constants

fun List<SubscriptionItemNetwork>.transformToDomain(): List<SubscriptionItemDomain> {
    return this.map {
        SubscriptionItemDomain(
            id = it.id,
            name = it.name,
            plan = it.plan,
            color = it.color,
            amount = it.amount,
            currency = it.currency,
            periodType = Constants.PeriodType.getPeriodType(it.periodType),
            date = it.date,
            subscriptionType = it.subscriptionType
        )
    }
}