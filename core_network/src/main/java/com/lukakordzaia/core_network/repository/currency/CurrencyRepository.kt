package com.lukakordzaia.core_network.repository.currency

import com.lukakordzaia.core_network.ResultNetwork
import com.lukakordzaia.core_network.networkmodels.CurrencyExchangeNetwork
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getCurrencyExchange(currencyFrom: String, currencyTo: String, amountFrom: Double): Flow<ResultNetwork<CurrencyExchangeNetwork>>
}