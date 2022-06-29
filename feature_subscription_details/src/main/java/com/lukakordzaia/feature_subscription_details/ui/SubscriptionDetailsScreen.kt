package com.lukakordzaia.feature_subscription_details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.core_compose.ObserveLoadingState
import com.lukakordzaia.core_compose.ObserveSingleEvents
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.feature_subscription_details.SubscriptionDetailsVM

@Composable
fun SubscriptionDetailsScreen(
    subscription: SubscriptionItemDomain,
    navHostController: NavHostController,
    vm: SubscriptionDetailsVM
) {
    val state = vm.state.collectAsState()
    vm.getSubscriptionDetails(subscription)
    ObserveSingleEvents(navController = navHostController, singleEvent = vm.singleEvent)
    ObserveLoadingState(loader = state.value.isLoading)

    state.value.details?.let { details ->
        DetailsWrapper(
            detailName = details.name,
            detailCurrency = details.currency,
            detailAmount = details.amount,
            detailPeriod = details.periodType,
            detailColor = details.color,
            nextPaymentDate = details.date.toString(),
            detailSubscriptionType = details.subscriptionType,
            detailPlan = details.plan,
            onBackClick = { navHostController.popBackStack() },
            onEditClick = { vm.navigateToEditSubscription(details) },
            onDeleteClick = { vm.deleteSubscription() }
        )
    }
}

@Composable
private fun DetailsWrapper(
    detailName: String,
    detailCurrency: String,
    detailAmount: Double,
    detailPeriod: Constants.PeriodType,
    detailColor: Color?,
    nextPaymentDate: String,
    detailSubscriptionType: Constants.SubscriptionType,
    detailPlan: String?,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = MaterialTheme.colors.background,
                shape = MaterialTheme.shapes.medium
            )
            .padding(10.dp)
    ) {
        NavigationBar(
            modifier = Modifier,
            click = onBackClick
        )
        DetailNameAmount(
            modifier = Modifier
                .padding(top = 30.dp)
                .align(Alignment.CenterHorizontally),
            detailName = detailName,
            detailCurrency = detailCurrency,
            detailAmount = detailAmount,
            detailPeriod = detailPeriod,
            detailColor = detailColor
        )
        SubscriptionInfo(
            modifier = Modifier
                .padding(top = 30.dp, start = 15.dp, end = 15.dp),
            nextPaymentDate = nextPaymentDate,
            paymentPeriod = detailPeriod,
            subscriptionType = detailSubscriptionType,
            subscriptionPlan = detailPlan
        )
        DetailButtons(
            modifier = Modifier
                .padding(vertical = 50.dp, horizontal = 20.dp),
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick
        )
    }
}

@Composable
private fun NavigationBar(
    modifier: Modifier,
    click: () -> Unit
) {
    Icon(
        modifier = modifier
            .clickable { click.invoke() }
            .padding(10.dp),
        imageVector = Icons.Filled.ArrowBack,
        contentDescription = null
    )
}