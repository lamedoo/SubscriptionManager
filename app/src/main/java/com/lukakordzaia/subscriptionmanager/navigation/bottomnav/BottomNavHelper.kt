package com.lukakordzaia.subscriptionmanager.navigation.bottomnav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lukakordzaia.core.R
import com.lukakordzaia.core.utils.NavConstants

object BottomNavHelper {
    sealed class BottomNav(val route: String, @StringRes val label: Int, @DrawableRes val icon: Int) {
        object Subscriptions : BottomNav(NavConstants.SUBSCRIPTIONS, R.string.subscriptions, R.drawable.icon_list)
        object Statistics: BottomNav(NavConstants.STATISTICS, R.string.statistics, R.drawable.icon_statistics)
    }

    val bottomNav = listOf(
        BottomNav.Subscriptions,
        BottomNav.Statistics
    )
}