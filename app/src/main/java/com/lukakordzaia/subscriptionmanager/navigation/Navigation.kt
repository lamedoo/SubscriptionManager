package com.lukakordzaia.subscriptionmanager.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.lukakordzaia.core.utils.NavConstants
import com.lukakordzaia.core.utils.fromJson
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.feature_add_subscription.ui.AddSubscriptionScreen
import com.lukakordzaia.feature_statistics.StatisticsScreen
import com.lukakordzaia.feature_subscription_details.SubscriptionDetailsVM
import com.lukakordzaia.feature_subscription_details.ui.SubscriptionDetailsScreen
import com.lukakordzaia.feature_subscriptions.SubscriptionsVM
import com.lukakordzaia.feature_subscriptions.ui.SubscriptionsScreen
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun GeneralNavGraph(padding: PaddingValues, navHostController: NavHostController, bottomSheetNavigator: BottomSheetNavigator) {

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp)
    ) {
        NavHost(
            modifier = Modifier
                .padding(padding),
            navController = navHostController,
            startDestination = NavConstants.SUBSCRIPTIONS,
            route = NavConstants.BOTTOM_NAV_ROUTE
        ) {
            composable(NavConstants.SUBSCRIPTIONS) {
                val viewModel = getViewModel<SubscriptionsVM>()
                SubscriptionsScreen(navHostController, viewModel)
            }
            composable(NavConstants.STATISTICS) {
                StatisticsScreen()
            }
            subscriptionDetailsNavGraph(navController = navHostController)
            composable(NavConstants.ADD_SUBSCRIPTION) {
                AddSubscriptionScreen(getViewModel(), navHostController)
            }
            editSubscriptionNavGraph(navController = navHostController)
        }
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
                    navHostController = navController,
                    vm = viewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.editSubscriptionNavGraph(navController: NavHostController) {
    navigation(startDestination = NavConstants.EDIT_SUBSCRIPTION, route = NavConstants.EDIT_ROUTE) {
        composable(
            route = NavConstants.EDIT_SUBSCRIPTION + "/{${NavConstants.SUBSCRIPTION_ARG}}",
            arguments = listOf(navArgument(NavConstants.SUBSCRIPTION_ARG) { type = NavType.StringType })
        ) {
            it.arguments?.getString(NavConstants.SUBSCRIPTION_ARG)?.let { jsonString ->
                val subscription = jsonString.fromJson(SubscriptionItemDomain::class.java)
                val viewModel = getViewModel<SubscriptionDetailsVM>()
                AddSubscriptionScreen(
                    vm = getViewModel(),
                    navHostController = navController,
                    subscriptionToEdit = subscription
                )
            }
        }
    }
}