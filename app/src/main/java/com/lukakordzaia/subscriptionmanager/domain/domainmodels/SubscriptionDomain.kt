package com.lukakordzaia.subscriptionmanager.domain.domainmodels

import androidx.compose.ui.graphics.Color
import com.lukakordzaia.subscriptionmanager.utils.Constants

data class SubscriptionDomain(
    val subscriptionType: String,
    val name: String,
    val plan: String,
    val color: Color,
    val currency: String,
    val amount: Double,
    val periodType: Constants.PeriodType,
    val date: Long,
)
