package com.lukakordzaia.subscriptionmanager.network

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.godaddy.android.colorpicker.HsvColor
import com.lukakordzaia.subscriptionmanager.domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.subscriptionmanager.network.networkmodels.SubscriptionItemNetwork
import com.lukakordzaia.subscriptionmanager.ui.theme._F1F1F5
import com.lukakordzaia.subscriptionmanager.utils.Constants

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
            subscriptionType = it.subscriptionType,
            updateDate = it.updateDate!!
        )
    }
}