package com.lukakordzaia.feature_home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lukakordzaia.core.R
import com.lukakordzaia.core.helpers.SingleEvent
import com.lukakordzaia.core.utils.LoadingState
import com.lukakordzaia.core_compose.custom.BoldText
import com.lukakordzaia.core_compose.custom.LightText
import com.lukakordzaia.core_compose.custom.ProgressDialog
import com.lukakordzaia.core_compose.theme._A6AEC0
import com.lukakordzaia.feature_home.HomeVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    vm: HomeVM
) {
    val state = vm.state.collectAsState()

    InitObserver(
        isLoading = state.value.isLoading,
        navController = navHostController,
        singleEvent = vm.singleEvent
    )

    Column(
        modifier = Modifier
            .padding(10.dp, 25.dp)
    ) {
        MonthlyCostBar(
            onMoreClick = { vm.navigateToStatistics() }
        )
        SubscriptionList(
            modifier = Modifier
                .padding(top = 20.dp),
            subscriptionItems = state.value.subscriptionItems,
            onCLick = { subscription -> vm.navigateToDetails(subscription) }
        )
        EmptyList(
            isEmpty = state.value.noSubscriptions,
            modifier = Modifier
                .padding(top = 10.dp)
        )
    }
}

@Composable
private fun EmptyList(
    isEmpty: Boolean,
    modifier: Modifier
) {
    if (isEmpty) {
        Column(
            modifier = modifier
        ) {
            LightText(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.no_subscriptions_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = _A6AEC0
            )
            BoldText(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.no_subscriptions_desc),
                fontSize = 24.sp,
                color = _A6AEC0
            )
        }
    }
}


@Composable
private fun InitObserver(
    isLoading: LoadingState,
    navController: NavHostController,
    singleEvent: Flow<SingleEvent>
) {

    when (isLoading) {
        LoadingState.LOADING -> ProgressDialog(showDialog = true)
        LoadingState.LOADED -> ProgressDialog(showDialog = false)
        LoadingState.ERROR -> {}
    }

    LaunchedEffect(key1 = null) {
        singleEvent.collectLatest {
            when (it) {
                is SingleEvent.Navigation -> {
                    navController.navigate(it.destination)
                }
            }
        }
    }
}