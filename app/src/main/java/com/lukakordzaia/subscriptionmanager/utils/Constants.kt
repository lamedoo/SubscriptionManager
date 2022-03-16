package com.lukakordzaia.subscriptionmanager.utils

object Constants {
    enum class PeriodType(val type: Int) {
        DAY(0),
        WEEK(1),
        MONTH(2),
        YEAR(3),
        UNKNOWN(4);

        companion object {
            fun getPeriodType(type: Int): PeriodType = values().firstOrNull { it.type == type } ?: UNKNOWN
        }
    }

    const val USERS_COLLECTION = "users"
    const val EMAIL = "email"
    const val SUBSCRIPTIONS_COLLECTION = "subscriptions"
}