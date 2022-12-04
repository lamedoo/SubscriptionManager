package com.lukakordzaia.core_domain.usecases

import com.lukakordzaia.core_domain.BaseFlowUseCase
import com.lukakordzaia.core_domain.ResultDomain
import com.lukakordzaia.core_domain.domainmodels.CurrencyExchangeDomain
import com.lukakordzaia.core_domain.transformToDomain
import com.lukakordzaia.core_network.networkmodels.CurrencyExchangeNetwork
import com.lukakordzaia.core_network.repository.currency.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class GetCurrencyExchangeUseCase(
    private val repository: CurrencyRepository
): BaseFlowUseCase<GetCurrencyExchangeUseCase.Params, CurrencyExchangeNetwork, CurrencyExchangeDomain>() {
    override suspend fun invoke(args: Params?): Flow<ResultDomain<CurrencyExchangeDomain, String>> {
        return transformToDomain(repository.getCurrencyExchange(args!!.currencyFrom, args.currencyTo, args.amountFrom)) { it.transformToDomain() }
    }

    data class Params(
        val currencyFrom: String,
        val currencyTo: String,
        val amountFrom: Double
    )
}