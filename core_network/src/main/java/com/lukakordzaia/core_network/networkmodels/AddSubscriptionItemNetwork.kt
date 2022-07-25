package com.lukakordzaia.core_network.networkmodels

data class AddSubscriptionItemNetwork(
    val id: String,
    val name: String,
    val plan: String?,
    val color: Int?,
    val amount: Double,
    val currency: String,
    val periodType: Int,
    val date: Long?,
    val subscriptionType: Int? = null,
    val updateDate: Long
)
