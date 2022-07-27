package com.lukakordzaia.feature_subscriptions.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lukakordzaia.core.R
import com.lukakordzaia.core_compose.ObserveLoadingState
import com.lukakordzaia.core_compose.ObserveSingleEvents
import com.lukakordzaia.core_compose.custom.BoldText
import com.lukakordzaia.core_compose.custom.LightText
import com.lukakordzaia.core_compose.theme._A6AEC0
import com.lukakordzaia.feature_subscriptions.SubscriptionsVM

@Composable
fun SubscriptionsScreen(
    navHostController: NavHostController,
    vm: SubscriptionsVM
) {
    val state = vm.state.collectAsState()
    ObserveSingleEvents(navController = navHostController, singleEvent = vm.singleEvent)
    ObserveLoadingState(loader = state.value.isLoading)

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 25.dp)
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