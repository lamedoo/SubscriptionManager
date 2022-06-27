package com.lukakordzaia.core_domain.domainmodels

import androidx.compose.ui.graphics.Color
import com.lukakordzaia.core.utils.Constants

data class SubscriptionItemDomain(
    val id: String,
    val name: String,
    val plan: String?,
    val color: Color?,
    val amount: Double,
    val currency: String,
    val periodType: Constants.PeriodType,
    val date: Long?,
    val subscriptionType: Constants.SubscriptionType,
    val updateDate: Long,
)
