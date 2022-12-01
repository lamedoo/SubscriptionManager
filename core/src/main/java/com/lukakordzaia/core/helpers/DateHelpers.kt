package com.lukakordzaia.core.helpers

import com.lukakordzaia.core.utils.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateHelpers {
    fun nextPaymentDate(periodType: Constants.PeriodType, date: Long): Long {
        val monthFormat = SimpleDateFormat("M", Locale.getDefault())
        val dayFormat = SimpleDateFormat("dd", Locale.getDefault())

        val dateInMonthDay = dayFormat.format(Date(date)).toInt()
        val currentInMonthDay = dayFormat.format(Date().time).toInt()

        val dateInMonth = monthFormat.format(Date(date)).toInt()
        val currentInMonth = monthFormat.format(Date().time).toInt()

        val firstPayment = Calendar.getInstance().apply { time = Date(date) }
        val currentDate = Calendar.getInstance()

        when (periodType) {
            Constants.PeriodType.WEEK -> {
                val currentDateInMillis = currentDate.apply {
                    set(Calendar.SECOND, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.HOUR, 0)
                }.timeInMillis

                val firstPaymentInMillis = firstPayment.apply {
                    set(Calendar.SECOND, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.HOUR, 0)
                }.timeInMillis

                val diff = currentDateInMillis - firstPaymentInMillis
                val dayCount = diff.toFloat() / (24 * 60 * 60 * 1000)

                val week = (dayCount / 7).toInt()
                val passedDays = (dayCount - (week * 7)).toInt()

                currentDate.add(Calendar.DAY_OF_MONTH, if (passedDays == 0) 0 else 7-passedDays)
            }
            Constants.PeriodType.MONTH -> {
                if (dateInMonthDay < currentInMonthDay) {
                    currentDate.set(Calendar.DAY_OF_MONTH, dateInMonthDay)
                    currentDate.add(Calendar.MONTH, 1)
                } else if (dateInMonthDay > currentInMonthDay) {
                    currentDate.set(Calendar.DAY_OF_MONTH, dateInMonthDay)
                }
            }
            Constants.PeriodType.YEAR -> {
                if (dateInMonth < currentInMonth) {
                    currentDate.add(Calendar.YEAR, 1)
                    currentDate.set(Calendar.DAY_OF_MONTH, dateInMonthDay)
                    currentDate.set(Calendar.MONTH, dateInMonth-1)
                } else if (dateInMonth > currentInMonth) {
                    currentDate.set(Calendar.DAY_OF_MONTH, dateInMonthDay)
                    currentDate.set(Calendar.MONTH, dateInMonth-1)
                } else {
                    if (dateInMonthDay < currentInMonthDay) {
                        currentDate.add(Calendar.YEAR, 1)
                        currentDate.set(Calendar.DAY_OF_MONTH, dateInMonthDay)
                    } else if (dateInMonthDay > currentInMonthDay) {
                        currentDate.set(Calendar.DAY_OF_MONTH, dateInMonthDay)
                    }
                }
            }
            else -> currentDate.add(Calendar.DAY_OF_MONTH, 0)
        }

        return currentDate.timeInMillis
    }

    fun formatDate(millis: Long, pattern: String = "dd MMMM, yyyy"): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(millis))
    }
}