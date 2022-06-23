package com.lukakordzaia.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lukakordzaia.core.R

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
            fun transformFromPeriodType(type: PeriodType): String {
                return when (type) {
                    DAY -> stringResource(id = R.string.day)
                    WEEK -> stringResource(id = R.string.week)
                    MONTH -> stringResource(id = R.string.month)
                    YEAR -> stringResource(id = R.string.year)
                    else -> stringResource(id = R.string.unknown)
                }
            }
        }
    }
}