package com.lukakordzaia.subscriptionmanager.network.networkmodels

import androidx.compose.ui.graphics.Color
import com.lukakordzaia.subscriptionmanager.utils.Constants

data class SubscriptionItemNetwork(
    val id: String,
    val name: String,
    val plan: String,
    val color: Color,
    val amount: Double,
    val currency: String,
    val periodType: Int,
    val date: Long,
    val subscriptionType: String
)
