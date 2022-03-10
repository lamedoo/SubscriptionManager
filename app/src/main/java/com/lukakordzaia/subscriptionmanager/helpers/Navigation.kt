package com.lukakordzaia.subscriptionmanager.helpers

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.lukakordzaia.subscriptionmanager.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigation {
    private val _destination = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val destination = _destination.asSharedFlow()

    sealed class BottomNav(val route: String, @StringRes val label: Int, val icon: ImageVector) {
        object Home : BottomNav(HOME, R.string.home, Icons.Filled.Home)
        object Statistics: BottomNav(STATISTICS, R.string.statistics, Icons.Filled.Settings)
    }

    fun navigateTo(navTarget: String) {
        _destination.tryEmit(navTarget)
    }

    companion object {
        const val HOME = "home"
        const val STATISTICS = "statistics"
        const val ADD_SUBSCRIPTION = "add_subscription"

        val bottomNav = listOf(
            BottomNav.Home,
            BottomNav.Statistics
        )
    }
}
