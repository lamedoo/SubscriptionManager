package com.lukakordzaia.feature_add_subscription.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lukakordzaia.core.R
import com.lukakordzaia.core.utils.NavConstants
import com.lukakordzaia.core_compose.ObserveLoadingState
import com.lukakordzaia.core_compose.ObserveSingleEvents
import com.lukakordzaia.core_compose.custom.BoldText
import com.lukakordzaia.core_compose.custom.CommonDialog
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.feature_add_subscription.AddSubscriptionVM
import com.lukakordzaia.feature_add_subscription.ui.fields.ColorField

@Composable
fun AddSubscriptionScreen(
    vm: AddSubscriptionVM,
    navHostController: NavHostController,
    subscriptionToEdit: SubscriptionItemDomain? = null
) {
    val state = vm.state.collectAsState().value
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    subscriptionToEdit?.let {
        if (!state.editIsSet) vm.setSubscriptionToEdit(it)
    }

    ObserveSingleEvents(navController = navHostController, singleEvent = vm.generalEvent) { it.popUpTo(NavConstants.SUBSCRIPTIONS) }
    ObserveLoadingState(
        loader = state.isLoading,
        isError = {
            CommonDialog(showDialog = state.errorDialogIsOpen, onDismiss = { state -> vm.setErrorDialogState(state) })
        }
    )

    StateObservers(
        isUploaded = state.isUploaded,
        onDone = { vm.navigateToDetails() }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(onClose = { navHostController.popBackStack() })
        ColorField(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp),
            value = state.colorField,
            colorDialogState = state.colorDialogIsOpen,
            onDialogStateChange = { state -> vm.setColorDialogState(state) },
            onChange = { value -> vm.setColor(value) }
        )
        NameField(
            value = state.nameField.field,
            onChange = { value -> vm.setName(value) },
            focusRequester = focusRequester,
            isError = state.nameField.isError
        )
        PlanField(
            value = state.planField,
            onChange = { value -> vm.setPlan(value) },
            focusRequester = focusRequester
        )
        SubscriptionTypeField(
            value = state.subscriptionTypeField,
            subscriptionTypeDialogState = state.subscriptionTypeDialogIsOpen,
            onDialogStateChange =  { state -> vm.setSubscriptionTypeDialogState(state) },
            onChange = {value -> vm.setSubscriptionType(transformToSubscriptionType(context, value))},
            focusRequester = focusRequester
        )
        CurrencyField(
            value = state.currencyField,
            currencyDialogState = state.currencyDialogIsOpen,
            onDialogStateChange = { state -> vm.setCurrencyDialogState(state) },
            onChange = { value -> vm.setCurrency(value) },
            focusRequester = focusRequester
        )
        AmountField(
            value = state.amountField.field,
            onChange = { value -> vm.setAmount(value) },
            focusRequester = focusRequester,
            isError = state.amountField.isError
        )
        DateField(
            value = state.dateField.field,
            onChange = { value -> vm.setDate(value) },
            focusRequester = focusRequester,
            isError = state.dateField.isError
        )
        PeriodField(
            value = state.periodField,
            periodDialogState = state.periodDialogIsOpen,
            onDialogStateChange = { state -> vm.setPeriodDialogState(state) },
            onChange = { value -> vm.setPeriod(transformToPeriodType(context, value)) },
            focusRequester = focusRequester
        )
        LinkField(
            value = state.linkField,
            onChange = { value -> vm.setLink(value) },
            focusRequester = focusRequester
        )
        Spacer(modifier = Modifier.weight(1f))
        AddButton(
            modifier = Modifier
                .weight(1f, false)
                .fillMaxWidth(),
            click = { vm.addSubscription() },
            text = stringResource(id = if (subscriptionToEdit != null) R.string.edit_subscription else R.string.add_subscription)
        )
    }
}

@Composable
private fun AddButton(modifier: Modifier, click: () -> Unit, text: String) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        content = { AddButtonView(text = text) },
        onClick = click
    )
}

@Composable
private fun AddButtonView(text: String) {
    BoldText(
        modifier = Modifier
            .padding(12.dp),
        text = text,
        fontSize = 18.sp
    )
}

@Composable
private fun StateObservers(
    isUploaded: Boolean,
    onDone: () -> Unit
) {
    if (isUploaded) {
        onDone.invoke()
    }
}

private fun transformToPeriodType(context: Context, type: String): Int {
    return when (type) {
        context.getString(R.string.day) -> 0
        context.getString(R.string.week) -> 1
        context.getString(R.string.month) -> 2
        context.getString(R.string.year) -> 3
        else -> 4
    }
}

private fun transformToSubscriptionType(context: Context, type: String): Int {
    return when (type) {
        context.getString(R.string.music) -> 0
        context.getString(R.string.entertainment) -> 1
        context.getString(R.string.online) -> 2
        context.getString(R.string.video_streaming) -> 3
        context.getString(R.string.profession) -> 4
        else -> 5
    }
}
