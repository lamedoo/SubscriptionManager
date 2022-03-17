package com.lukakordzaia.subscriptionmanager.utils

object Currencies {
    enum class Currency(val code: String, val symbol: String) {
        GEL(Currencies.GEL, GEL_SYMBOL),
        USD(Currencies.USD, USD_SYMBOL),
        EUR(Currencies.EUR, EUR_SYMBOL),
        GBP(Currencies.GBP, GBP_SYMBOL);

        companion object {
            fun getCurrencySymbol(code: String): String {
                return values().find { it.code == code.uppercase() }?.symbol ?: code
            }

            fun getCurrencyCode(symbol: String): String {
                return values().find { it.symbol == symbol }?.code ?: symbol
            }

            fun getCurrencyList(): List<Currency> = values().toList()
        }
    }

    const val GEL_SYMBOL = "\u20BE"
    const val USD_SYMBOL = "$"
    const val EUR_SYMBOL = "€"
    const val GBP_SYMBOL = "£"

    const val GEL = "GEL"
    const val EUR = "EUR"
    const val USD = "USD"
    const val GBP = "GBP"
}