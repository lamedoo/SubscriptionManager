package com.lukakordzaia.subscriptionmanager.navigation.bottomnav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.lukakordzaia.core.R
import com.lukakordzaia.core.utils.NavConstants

object BottomNavHelper {
    sealed class BottomNav(val route: String, @StringRes val label: Int, val icon: ImageVector) {
        object Home : BottomNav(NavConstants.HOME, R.string.home, Icons.Filled.Home)
        object Statistics: BottomNav(NavConstants.STATISTICS, R.string.statistics, Icons.Filled.Settings)
    }

    val bottomNav = listOf(
        BottomNav.Home,
        BottomNav.Statistics
    )
}