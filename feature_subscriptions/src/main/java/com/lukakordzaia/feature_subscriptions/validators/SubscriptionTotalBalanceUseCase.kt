package com.lukakordzaia.feature_subscriptions.validators

import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.core.utils.Currencies
import com.lukakordzaia.core_domain.BaseUseCase
import com.lukakordzaia.core_domain.ResultDomain
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.core_domain.usecases.GetCurrencyExchangeUseCase
import java.math.BigDecimal
import java.math.RoundingMode

class SubscriptionTotalBalanceUseCase(
    private val getCurrencyExchangeUseCase: GetCurrencyExchangeUseCase
) : BaseUseCase<List<SubscriptionItemDomain>, Double> {
    override suspend fun invoke(args: List<SubscriptionItemDomain>?): Double {
        var totalBalance = 0.0

        args?.let { subscription ->
            subscription.forEach {
                totalBalance += exchangeCurrency(currencyFrom = it.currency, amountFrom = it.amount, period = it.periodType)
            }
        }

        return BigDecimal(totalBalance.toString()).setScale(2, RoundingMode.HALF_UP).toDouble()
    }

    private suspend fun exchangeCurrency(currencyFrom: String, currencyTo: String = Currencies.USD, amountFrom: Double, period: Constants.PeriodType): Double {
        var amount = 0.0

        try {
            getCurrencyExchangeUseCase.invoke(GetCurrencyExchangeUseCase.Params(currencyFrom, currencyTo, amountFrom)).collect {
                amount = when (it) {
                    is ResultDomain.Success -> {
                        val data = it.data.data.amount

                        when (period) {
                            Constants.PeriodType.MONTH -> data
                            Constants.PeriodType.YEAR -> data / 12
                            Constants.PeriodType.DAY -> data * 30
                            Constants.PeriodType.WEEK -> data * 4
                            else -> data
                        }
                    }
                    is ResultDomain.Error -> 0.0
                }
            }
        } catch (_: Exception) {}

        return amount
    }
}