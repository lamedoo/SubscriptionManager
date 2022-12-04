package com.lukakordzaia.core_network.networkmodels

data class CurrencyExchangeNetwork(
    val data: ExchangeDataNetwork
) {
    data class ExchangeDataNetwork(
        val amount: Double,
        val rate: Double,
        val amountSelf: Double,
        val rateSelf: Double,
        val rateDifference: Double,
        val baseCcy: String,
        val baseCcyRateWeight: Int
    )
}
