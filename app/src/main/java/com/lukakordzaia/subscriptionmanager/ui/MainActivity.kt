package com.lukakordzaia.subscriptionmanager.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lukakordzaia.core.utils.NavConstants
import com.lukakordzaia.core_compose.theme.SubscriptionManagerTheme
import com.lukakordzaia.feature_add_subscription.AddSubscriptionScreen
import com.lukakordzaia.subscriptionmanager.navigation.GeneralNavGraph
import com.lukakordzaia.subscriptionmanager.navigation.bottomnav.BottomNavigationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

class MainActivity : com.lukakordzaia.core.activity.BaseComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MainContent() {
    SubscriptionManagerTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            AddSubscriptionScaffold()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun AddSubscriptionScaffold() {
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, confirmStateChange = { false })
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = { AddSubscriptionScreen(getViewModel(), modalBottomSheetState, coroutineScope) },
        sheetShape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp)
    ) {
        MainScaffold(modalBottomSheetState, coroutineScope)
    }
}

@ExperimentalMaterialApi
@Composable
fun MainScaffold(state: ModalBottomSheetState, scope: CoroutineScope) {
    val bottomBarState = remember { (mutableStateOf(true)) }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        NavConstants.SUBSCRIPTIONS, NavConstants.STATISTICS -> bottomBarState.value = true
        else -> bottomBarState.value = false
    }

    Scaffold(
        bottomBar = { BottomNavigationComponent(navController = navController, bottomBarState) } ,
        floatingActionButton = { AddButton(
            bottomBarState = bottomBarState,
            click = {
                scope.launch {
                    state.animateTo(ModalBottomSheetValue.Expanded)
                }
            }) },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        GeneralNavGraph(padding = padding, navController = navController)
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
            onClick = click
        )
    }
}

@Composable
fun AddButtonView() {
    Icon(
        imageVector = Icons.Filled.Add,
        contentDescription = "Add button",
        modifier = Modifier.size(35.dp),
        tint = MaterialTheme.colors.onSecondary
    )
}