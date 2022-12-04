package com.lukakordzaia.core_domain.domainmodels

data class CurrencyExchangeDomain(
    val data: ExchangeDataDomain
) {
    data class ExchangeDataDomain(
        val amount: Double,
        val rate: Double,
        val amountSelf: Double,
        val rateSelf: Double,
        val rateDifference: Double,
        val baseCcy: String,
        val baseCcyRateWeight: Int
    )
}
