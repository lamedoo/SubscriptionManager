package com.lukakordzaia.subscriptionmanager.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.lukakordzaia.subscriptionmanager.ui.theme.SubscriptionManagerTheme
import com.lukakordzaia.subscriptionmanager.ui.addsubscription.AddSubscriptionScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
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
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = { AddSubscriptionScreen() },
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp)
    ) {
        MainScaffold(bottomSheetScaffoldState)
    }
}

@ExperimentalMaterialApi
@Composable
fun MainScaffold(state: BottomSheetScaffoldState) {
    val navController = rememberNavController()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { BottomNavigationComponent(navController = navController) } ,
        floatingActionButton = { AddButton(state = state, scope = coroutineScope) },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        BottomNavGraph(navController = navController)
    }
}

@ExperimentalMaterialApi
@Composable
fun AddButton(state: BottomSheetScaffoldState, scope: CoroutineScope) {
    FloatingActionButton(
        content = { AddButtonView() },
        onClick = {
            scope.launch {
                state.bottomSheetState.expand()
            }
        }
    )
}

@ExperimentalMaterialApi
@Composable
fun AddButtonView() {
    Icon(
        imageVector = Icons.Filled.Add,
        contentDescription = "Add button",
        modifier = Modifier.size(35.dp),
        tint = MaterialTheme.colors.onSecondary
    )
}