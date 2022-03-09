package com.lukakordzaia.subscriptionmanager.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavigatorProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lukakordzaia.subscriptionmanager.ui.home.HomeScreen
import com.lukakordzaia.subscriptionmanager.ui.theme.SubscriptionManagerTheme
import com.lukakordzaia.subscriptionmanager.utils.Constants
import com.lukakordzaia.subscriptionmanager.utils.Destinations

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            SubscriptionManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationComponent(navController = navController)
                }
            }
        }
    }
}

@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destinations.HOME) {
        composable(Destinations.HOME) {
            Home(navController = navController)
        }
        composable(Destinations.ADD_SUBSCRIPTION) {

        }
    }
}

@Composable
fun Home(navController: NavHostController) {
    HomeScreen(addButtonClick = { navController.navigate(Destinations.ADD_SUBSCRIPTION) })
}