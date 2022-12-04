package com.lukakordzaia.subscriptionmanager.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.lukakordzaia.core.activity.BaseComponentActivity
import com.lukakordzaia.core.utils.NavConstants
import com.lukakordzaia.core_compose.theme.SubscriptionManagerTheme
import com.lukakordzaia.subscriptionmanager.navigation.GeneralNavGraph
import com.lukakordzaia.subscriptionmanager.navigation.bottomnav.BottomNavigationComponent

class MainActivity : BaseComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@Composable
fun MainContent() {
    SubscriptionManagerTheme {
        Surface{
            MainScaffold()
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScaffold() {
    val bottomBarState = remember { (mutableStateOf(true)) }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val bottomSheetNavigator = remember { BottomSheetNavigator(sheetState) }
    val navController = rememberNavController(bottomSheetNavigator)
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        NavConstants.SUBSCRIPTIONS, NavConstants.STATISTICS -> bottomBarState.value = true
        else -> bottomBarState.value = false
    }

    Scaffold(
        bottomBar = { BottomNavigationComponent(navController = navController, bottomBarState) } ,
        floatingActionButton = {
            AddButton(
                bottomBarState = bottomBarState,
                click = { (navController as NavHostController).navigate(NavConstants.ADD_SUBSCRIPTION) }
            )
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        backgroundColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        GeneralNavGraph(padding = padding, navHostController = navController, bottomSheetNavigator = bottomSheetNavigator)
    }
}

@ExperimentalMaterialApi
@Composable
fun AddButton(
    bottomBarState: MutableState<Boolean>,
    click: () -> Unit
) {
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        FloatingActionButton(
            content = { AddButtonView() },
            onClick = click,
            backgroundColor = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun AddButtonView() {
    Icon(
        imageVector = Icons.Filled.Add,
        contentDescription = "Add button",
        modifier = Modifier.size(35.dp),
        tint = MaterialTheme.colorScheme.onSecondary
    )
}