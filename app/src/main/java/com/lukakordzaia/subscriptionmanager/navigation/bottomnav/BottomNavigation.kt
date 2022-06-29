package com.lukakordzaia.subscriptionmanager.navigation.bottomnav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lukakordzaia.core_compose.theme._A6AEC0
import com.lukakordzaia.core_compose.theme.bottomNavLabelStyle
import com.lukakordzaia.subscriptionmanager.navigation.bottomnav.BottomNavHelper.bottomNav

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

                bottomNav.forEach { item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp),
                                painter = painterResource(id = item.icon),
                                contentDescription = null
                            )
                               },
                        label = { Text(text = stringResource(id = item.label), style = bottomNavLabelStyle) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
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