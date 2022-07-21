package com.lukakordzaia.core_domain

import androidx.compose.ui.graphics.Color
import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.subscriptionmanager.network.networkmodels.SubscriptionItemNetwork

fun List<SubscriptionItemNetwork>.transformToDomain(): List<SubscriptionItemDomain> {
    return this.map {
        SubscriptionItemDomain(
            id = it.id!!,
            name = it.name!!,
            plan = it.plan,
            color = it.color?.let { color -> Color(color) },
            amount = it.amount!!,
            currency = it.currency!!,
            periodType = Constants.PeriodType.getPeriodType(it.periodType!!),
            date = it.date,
            subscriptionType = Constants.SubscriptionType.getSubscriptionType(it.subscriptionType!!),
            updateDate = it.updateDate!!
        )
    }
}