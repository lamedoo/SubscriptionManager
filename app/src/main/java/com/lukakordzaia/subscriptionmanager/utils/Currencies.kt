package com.lukakordzaia.subscriptionmanager.utils

object Currencies {
    enum class Currency(val code: String, val symbol: String) {
        GEL(Currencies.GEL, LARI_SYMBOL),
        USD(Currencies.USD, DOLLAR_SYMBOL),
        EUR(Currencies.EUR, EURO_SYMBOL),
        GBP(Currencies.GBP, POUND_SYMBOL);

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

    const val LARI_SYMBOL = "\u20BE"
    const val DOLLAR_SYMBOL = "$"
    const val EURO_SYMBOL = "€"
    const val POUND_SYMBOL = "£"
    const val GEL = "GEL"
    const val EUR = "EUR"
    const val USD = "USD"
    const val GBP = "GBP"
}