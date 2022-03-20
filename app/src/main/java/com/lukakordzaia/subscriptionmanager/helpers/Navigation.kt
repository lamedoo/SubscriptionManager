package com.lukakordzaia.subscriptionmanager.helpers

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lukakordzaia.subscriptionmanager.R
import com.lukakordzaia.subscriptionmanager.domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.subscriptionmanager.ui.main.home.HomeScreen
import com.lukakordzaia.subscriptionmanager.ui.main.home.HomeVM
import com.lukakordzaia.subscriptionmanager.ui.main.statistics.StatisticsScreen
import com.lukakordzaia.subscriptionmanager.ui.main.subscriptiondetails.SubscriptionDetailsScreen
import com.lukakordzaia.subscriptionmanager.ui.main.subscriptiondetails.SubscriptionDetailsVM
import com.lukakordzaia.subscriptionmanager.utils.fromJson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.koin.androidx.compose.getViewModel

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
        // Destinations
        const val HOME = "home"
        const val STATISTICS = "statistics"
        const val SUBSCRIPTION_DETAILS = "subscription_details"

        // Routes
        const val BOTTOM_NAV_ROUTE = "bottom_route"
        const val DETAILS_ROUTE = "details_route"

        // Args
        const val SUBSCRIPTION_ARG = "subscription"

        val bottomNav = listOf(
            BottomNav.Home,
            BottomNav.Statistics
        )
    }
}

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Navigation.HOME, route = Navigation.BOTTOM_NAV_ROUTE) {
        composable(Navigation.HOME) {
            val viewModel = getViewModel<HomeVM>()
            HomeScreen(navController, viewModel)
        }
        composable(Navigation.STATISTICS) {
            StatisticsScreen()
        }
        subscriptionDetailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.subscriptionDetailsNavGraph(navController: NavHostController) {
    navigation(startDestination = Navigation.SUBSCRIPTION_DETAILS, route = Navigation.DETAILS_ROUTE) {
        composable(
            route = Navigation.SUBSCRIPTION_DETAILS + "/{${Navigation.SUBSCRIPTION_ARG}}",
            arguments = listOf(navArgument(Navigation.SUBSCRIPTION_ARG) { type = NavType.StringType })
        ) {
            it.arguments?.getString(Navigation.SUBSCRIPTION_ARG)?.let { jsonString ->
                val subscription = jsonString.fromJson(SubscriptionItemDomain::class.java)
                val viewModel = getViewModel<SubscriptionDetailsVM>()
                SubscriptionDetailsScreen(
                    subscription = subscription,
                    navController = navController,
                    vm = viewModel
                )
            }
        }
    }
}