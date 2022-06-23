package com.lukakordzaia.subscriptionmanager.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lukakordzaia.core.utils.NavConstants
import com.lukakordzaia.core.utils.fromJson
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.feature_statistics.StatisticsScreen
import com.lukakordzaia.subscriptionmanager.ui.navigation.subscriptiondetails.SubscriptionDetailsScreen
import com.lukakordzaia.subscriptionmanager.ui.navigation.subscriptiondetails.SubscriptionDetailsVM
import org.koin.androidx.compose.getViewModel

@Composable
fun GeneralNavGraph(padding: PaddingValues, navController: NavHostController) {
    NavHost(
        modifier = Modifier
            .padding(padding),
        navController = navController,
        startDestination = NavConstants.HOME,
        route = NavConstants.BOTTOM_NAV_ROUTE
    ) {
        composable(NavConstants.HOME) {
            val viewModel = getViewModel<com.lukakordzaia.feature_home.HomeVM>()
            com.lukakordzaia.feature_home.HomeScreen(navController, viewModel)
        }
        composable(NavConstants.STATISTICS) {
            StatisticsScreen()
        }
        subscriptionDetailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.subscriptionDetailsNavGraph(navController: NavHostController) {
    navigation(startDestination = NavConstants.SUBSCRIPTION_DETAILS, route = NavConstants.DETAILS_ROUTE) {
        composable(
            route = NavConstants.SUBSCRIPTION_DETAILS + "/{${NavConstants.SUBSCRIPTION_ARG}}",
            arguments = listOf(navArgument(NavConstants.SUBSCRIPTION_ARG) { type = NavType.StringType })
        ) {
            it.arguments?.getString(NavConstants.SUBSCRIPTION_ARG)?.let { jsonString ->
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