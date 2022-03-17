package com.lukakordzaia.subscriptionmanager.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lukakordzaia.subscriptionmanager.R

object Constants {
    enum class PeriodType(val type: Int) {
        DAY(0),
        WEEK(1),
        MONTH(2),
        YEAR(3),
        UNKNOWN(4);

        companion object {
            fun getPeriodType(type: Int): PeriodType = values().firstOrNull { it.type == type } ?: UNKNOWN

            @Composable
            fun transformFromPeriodType(type: Constants.PeriodType): String {
                return when (type) {
                    Constants.PeriodType.DAY -> stringResource(id = R.string.day)
                    Constants.PeriodType.WEEK -> stringResource(id = R.string.week)
                    Constants.PeriodType.MONTH -> stringResource(id = R.string.month)
                    Constants.PeriodType.YEAR -> stringResource(id = R.string.year)
                    else -> stringResource(id = R.string.unknown)
                }
            }
        }
    }

    const val USERS_COLLECTION = "users"
    const val EMAIL = "email"
    const val SUBSCRIPTIONS_COLLECTION = "subscriptions"
}