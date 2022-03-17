package com.lukakordzaia.subscriptionmanager.domain.domainmodels

import androidx.compose.ui.graphics.Color
import com.lukakordzaia.subscriptionmanager.utils.Constants

data class SubscriptionItemDomain(
    val id: String,
    val name: String,
    val plan: String?,
    val color: Color?,
    val amount: Double,
    val currency: String,
    val periodType: Constants.PeriodType,
    val date: Long?,
    val subscriptionType: String?
)
