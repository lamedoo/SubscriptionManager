package com.lukakordzaia.subscriptionmanager.domain.domainmodels

import androidx.compose.ui.graphics.Color
import com.lukakordzaia.subscriptionmanager.utils.Constants

data class SubscriptionDomain(
    val subscriptionType: String,
    val name: String,
    val description: String,
    val color: Color,
    val currency: String,
    val amount: Double,
    val periodType: Constants.PeriodType
)
