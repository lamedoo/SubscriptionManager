package com.lukakordzaia.core_domain

import androidx.compose.ui.graphics.Color
import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.subscriptionmanager.network.networkmodels.SubscriptionItemNetwork

fun List<SubscriptionItemNetwork>.transformToDomain(): List<com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain> {
    return this.map {
        com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain(
            id = it.id!!,
            name = it.name!!,
            plan = it.plan,
            color = it.color?.let { color -> Color(color) },
            amount = it.amount!!,
            currency = it.currency!!,
            periodType = Constants.PeriodType.getPeriodType(it.periodType!!),
            date = it.date,
            subscriptionType = it.subscriptionType,
            updateDate = it.updateDate!!
        )
    }
}