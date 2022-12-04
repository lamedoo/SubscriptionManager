package com.lukakordzaia.core_network.api

import com.lukakordzaia.core_network.networkmodels.CurrencyExchangeNetwork
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyService {
    @GET("convert/{currencyFrom}/{currencyTo}")
    suspend fun getCurrencyExchange(
        @Path("currencyFrom") currencyFrom: String,
        @Path("currencyTo") currencyTo: String,
        @Query("amountFrom") amountFrom: Double): Response<CurrencyExchangeNetwork>
}