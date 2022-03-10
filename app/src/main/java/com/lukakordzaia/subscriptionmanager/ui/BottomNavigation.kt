package com.lukakordzaia.subscriptionmanager.ui

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lukakordzaia.subscriptionmanager.ui.home.HomeScreen
import com.lukakordzaia.subscriptionmanager.ui.home.HomeViewModel
import com.lukakordzaia.subscriptionmanager.ui.statistics.StatisticsScreen
import com.lukakordzaia.subscriptionmanager.helpers.Navigation
import com.lukakordzaia.subscriptionmanager.ui.theme._A6AEC0
import com.lukakordzaia.subscriptionmanager.ui.theme._C4C9D7
import org.koin.androidx.compose.getViewModel

@Composable
fun BottomNavigationComponent(navController: NavHostController) {
    BottomAppBar(
        cutoutShape = CircleShape
    ) {
        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            Navigation.bottomNav.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(imageVector = item.icon, contentDescription = null) },
                    label = { Text(text = stringResource(id = item.label)) },
                    selected = currentDestination?.route == item.route,
                    selectedContentColor = MaterialTheme.colors.secondary,
                    unselectedContentColor = _A6AEC0,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Navigation.HOME) {
        composable(Navigation.HOME) {
            val viewModel = getViewModel<HomeViewModel>()
            HomeScreen(viewModel)
        }
        composable(Navigation.STATISTICS) {
            StatisticsScreen()
        }
    }
}