package com.lukakordzaia.subscriptionmanager.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lukakordzaia.subscriptionmanager.ui.main.home.HomeScreen
import com.lukakordzaia.subscriptionmanager.ui.main.home.HomeVM
import com.lukakordzaia.subscriptionmanager.ui.main.statistics.StatisticsScreen
import com.lukakordzaia.subscriptionmanager.helpers.Navigation
import com.lukakordzaia.subscriptionmanager.ui.theme._A6AEC0
import com.lukakordzaia.subscriptionmanager.ui.theme.bottomNavLabelStyle
import org.koin.androidx.compose.getViewModel

@Composable
fun BottomNavigationComponent(navController: NavHostController, bottomBarState: MutableState<Boolean>) {
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomAppBar(
            cutoutShape = CircleShape
        ) {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Navigation.bottomNav.forEach { item ->
                    BottomNavigationItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = item.label), style = bottomNavLabelStyle) },
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
}