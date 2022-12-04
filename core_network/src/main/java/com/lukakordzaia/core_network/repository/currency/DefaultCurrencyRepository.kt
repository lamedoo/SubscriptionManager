package com.lukakordzaia.core_network.repository.currency

import com.lukakordzaia.core_network.ResultNetwork
import com.lukakordzaia.core_network.api.ApiCallWrapper
import com.lukakordzaia.core_network.api.CurrencyService
import com.lukakordzaia.core_network.networkmodels.CurrencyExchangeNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultCurrencyRepository(private val service: CurrencyService, private val apiCallWrapper: ApiCallWrapper): CurrencyRepository {
    override suspend fun getCurrencyExchange(currencyFrom: String, currencyTo: String, amountFrom: Double): Flow<ResultNetwork<CurrencyExchangeNetwork>> {
        return flow { emit(apiCallWrapper.makeCall { service.getCurrencyExchange(currencyFrom, currencyTo, amountFrom) }) }
    }
}