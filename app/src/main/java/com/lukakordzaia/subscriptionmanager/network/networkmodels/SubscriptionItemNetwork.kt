package com.lukakordzaia.subscriptionmanager.network.networkmodels

import androidx.compose.ui.graphics.Color
import com.lukakordzaia.subscriptionmanager.utils.Constants

data class SubscriptionItemNetwork(
    val id: String? = null,
    val name: String? = null,
    val plan: String? = null,
    val color: Int? = null,
    val amount: Double? = null,
    val currency: String? = null,
    val periodType: Int? = null,
    val date: Long? = null,
    val subscriptionType: String? = null
)
