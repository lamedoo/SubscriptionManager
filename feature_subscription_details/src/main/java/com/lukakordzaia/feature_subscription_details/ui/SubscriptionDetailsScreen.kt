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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lukakordzaia.core.R
import com.lukakordzaia.core.helpers.DateHelpers
import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.core.utils.NavConstants
import com.lukakordzaia.core_compose.ObserveLoadingState
import com.lukakordzaia.core_compose.ObserveSingleEvents
import com.lukakordzaia.core_compose.custom.QuestionDialog
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.feature_subscription_details.SubscriptionDetailsVM

@Composable
fun SubscriptionDetailsScreen(
    subscription: SubscriptionItemDomain,
    navHostController: NavHostController,
    vm: SubscriptionDetailsVM
) {
    val state = vm.state.collectAsState().value
    vm.getSubscriptionDetails(subscription)
    ObserveSingleEvents(navController = navHostController, singleEvent = vm.generalEvent)
    ObserveLoadingState(loader = state.loadingState)
    ObserveDeleteDialog(
        deleteDialogState = state.deleteDialogIsOpen,
        onDeleteDialogStateChange = { dialogState -> vm.setDeleteDialogState(dialogState) },
        onDeleteDialogConfirm = { state.details?.id?.let { vm.deleteSubscription(it) } }
    )
    ObserveDeleteStatus(isDeleted = state.isSubscriptionDeleted) {
        vm.deleteSubscription(stringResource(id = R.string.deleted_successfully))
        navHostController.popBackStack(route = NavConstants.SUBSCRIPTIONS, inclusive = false, saveState = false)
    }
    state.details?.let { details ->
        DetailsWrapper(
            detailName = details.name,
            detailCurrency = details.currency,
            detailAmount = details.amount,
            detailPeriod = details.periodType,
            detailColor = details.color,
            firstPaymentDay = details.date,
            nextPaymentDate = DateHelpers.formatDate(DateHelpers.nextPaymentDate(details.periodType, details.date!!)),
            detailSubscriptionType = details.subscriptionType,
            detailPlan = details.plan,
            onBackClick = { navHostController.popBackStack() },
            onEditClick = { vm.navigateToEditSubscription(details) },
            onDeleteClick = { vm.setDeleteDialogState(true) }
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
    firstPaymentDay: Long?,
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
            firstPaymentDay = firstPaymentDay,
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

@Composable
private fun ObserveDeleteDialog(
    deleteDialogState: Boolean,
    onDeleteDialogStateChange: (Boolean) -> Unit,
    onDeleteDialogConfirm: () -> Unit
) {
    QuestionDialog(
        showDialog = deleteDialogState,
        onDismiss = { state -> onDeleteDialogStateChange(state) },
        question = stringResource(id = R.string.delete_subscription_dialog_text),
        yestButtonText = stringResource(id = R.string.delete),
        onConfirm = onDeleteDialogConfirm
    )
}

@Composable
private fun ObserveDeleteStatus(
    isDeleted: Boolean,
    onDelete: @Composable () -> Unit
) {
    if (isDeleted) {
        onDelete.invoke()
    }
}