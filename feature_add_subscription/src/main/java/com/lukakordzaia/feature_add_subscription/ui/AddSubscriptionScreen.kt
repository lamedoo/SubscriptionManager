package com.lukakordzaia.feature_add_subscription.ui

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.lukakordzaia.core.R
import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.core.utils.Constants.PeriodType.Companion.transformFromPeriodType
import com.lukakordzaia.core_compose.ObserveLoadingState
import com.lukakordzaia.core_compose.custom.CommonDialog
import com.lukakordzaia.feature_add_subscription.AddSubscriptionVM
import java.util.*

@Composable
fun AddSubscriptionScreen(
    vm: AddSubscriptionVM,
    navHostController: NavHostController
) {
    val state = vm.state.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    ObserveLoadingState(
        loader = state.value.isLoading,
        isError = {
            CommonDialog(showDialog = state.value.errorDialogIsOpen, onDismiss = { state -> vm.setErrorDialogState(state) })
        }
    )

    StateObservers(
        isUploaded = state.value.isUploaded,
        onDone = { navHostController.popBackStack() }
    )

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val (topBar,
            link,
            name,
            plan,
            color,
            amountCurrency,
            period,
            date,
            button,
            subscriptionType) = createRefs()

        TopBar(
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onClose = { navHostController.popBackStack() }
        )
        NameField(
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(topBar.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth(0.67F),
            value = state.value.nameField.field,
            onChange = { value -> vm.setName(value) },
            focusRequester = focusRequester,
            isError = state.value.nameField.isError
        )
        PlanField(
            modifier = Modifier
                .constrainAs(plan) {
                    top.linkTo(name.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth(0.67F),
            value = state.value.planField,
            onChange = { value -> vm.setPlan(value) },
            focusRequester = focusRequester
        )
        ColorField(
            modifier = Modifier
                .constrainAs(color) {
                    top.linkTo(name.top)
                    bottom.linkTo(amountCurrency.top, margin = 10.dp)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .fillMaxWidth(0.3F),
            value = state.value.colorField ?: MaterialTheme.colors.background,
            colorDialogState = state.value.colorDialogIsOpen,
            onDialogStateChange = { state -> vm.setColorDialogState(state) },
            onChange = { value -> vm.setColor(value) }
        )

        AmountCurrencyField(
            modifier = Modifier
                .constrainAs(amountCurrency) {
                    top.linkTo(plan.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth(),
            amountValue = state.value.amountField.field,
            onAmountChange = { value -> vm.setAmount(value) },
            amountIsError = state.value.amountField.isError,
            currencyValue = state.value.currencyField,
            currencyDialogState = state.value.currencyDialogIsOpen,
            onCurrencyDialogStateChange = { state -> vm.setCurrencyDialogState(state) },
            onCurrencyChange = { value -> vm.setCurrency(value.substring(0, 3)) },
            focusRequester = focusRequester
        )

        PeriodField(
            modifier = Modifier
                .constrainAs(period) {
                    top.linkTo(amountCurrency.bottom, margin = 10.dp)
                }
                .fillMaxWidth(),
            value = state.value.periodField,
            periodDialogState = state.value.periodDialogIsOpen,
            onDialogStateChange = { state -> vm.setPeriodDialogState(state) },
            onChange = { value -> vm.setPeriod(transformToPeriodType(context, value)) },
            focusRequester = focusRequester
        )
        DateField(
            modifier = Modifier
                .constrainAs(date) {
                    top.linkTo(period.bottom, margin = 10.dp)
                }
                .fillMaxWidth(),
            value = state.value.dateField.field,
            onChange = { value -> vm.setDate(value) },
            focusRequester = focusRequester,
            isError = state.value.dateField.isError
        )
        SubscriptionTypeField(modifier = Modifier
            .constrainAs(subscriptionType) {
                top.linkTo(date.bottom, margin = 10.dp)
            }
            .fillMaxWidth(),
            value = state.value.subscriptionTypeField,
            subscriptionTypeDialogState = state.value.subscriptionTypeDialogIsOpen,
            onDialogStateChange =  { state -> vm.setSubscriptionTypeDialogState(state) },
            onChange = {value -> vm.setSubscriptionType(transformToSubscriptionType(context, value))},
            focusRequester = focusRequester
        )
        LinkField(
            modifier = Modifier
                .constrainAs(link) {
                    top.linkTo(subscriptionType.bottom, margin = 10.dp)
                }
                .fillMaxWidth(),
            value = state.value.linkField,
            onChange = { value -> vm.setLink(value) },
            focusRequester = focusRequester
        )
        AddButton(
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(link.bottom, margin = 30.dp)
                }
                .fillMaxWidth(),
            click = { vm.addSubscription() }
        )
    }
}

@Composable
private fun NameField(
    modifier: Modifier,
    value: String,
    onChange: (name: String) -> Unit,
    focusRequester: FocusRequester,
    isError: Boolean
) {
    CustomTextField(
        modifier = modifier,
        label = stringResource(id = R.string.name),
        value = value,
        onChange = onChange,
        focusRequester = focusRequester,
        isError = isError
    )
}

@Composable
private fun PlanField(
    modifier: Modifier,
    value: String,
    onChange: (plan: String) -> Unit,
    focusRequester: FocusRequester
) {
    CustomTextField(
        modifier = modifier,
        label = stringResource(id = R.string.plan),
        value = value,
        onChange = onChange,
        focusRequester = focusRequester
    )
}

@Composable
private fun LinkField(
    modifier: Modifier,
    value: String,
    onChange: (link: String) -> Unit,
    focusRequester: FocusRequester
) {
    CustomTextField(
        modifier = modifier,
        label = stringResource(id = R.string.link),
        value = value,
        onChange = onChange,
        focusRequester = focusRequester,
        imeAction = ImeAction.Done
    )
}

@Composable
private fun DateField(
    modifier: Modifier,
    value: String,
    onChange: (date: String) -> Unit,
    focusRequester: FocusRequester,
    isError: Boolean
) {
    val c = Calendar.getInstance()
    val timePickerDialog = DatePickerDialog(LocalContext.current,
        {_, year: Int, month : Int, day: Int ->
            onChange("$day/${month + 1}/$year")
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
    )
    Box(
        modifier = modifier
    ) {
        CustomTextField(
            modifier = modifier,
            label = stringResource(id = R.string.payment_day),
            value = value,
            onChange = onChange,
            focusRequester = focusRequester,
            isError = isError
        )
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .clickable(
                    onClick = { timePickerDialog.show() }
                )
        )
    }
}

@Composable
private fun PeriodField(
    modifier: Modifier,
    value: Int,
    periodDialogState: Boolean,
    onDialogStateChange: (Boolean) -> Unit,
    onChange: (period: String) -> Unit,
    focusRequester: FocusRequester
) {
    val periodList = listOf(
        stringResource(id = R.string.month),
        stringResource(id = R.string.day),
        stringResource(id = R.string.week),
        stringResource(id = R.string.year)
    )

    Box(
        modifier = modifier
    ) {
        CustomTextField(
            modifier = modifier,
            label = stringResource(id = R.string.period),
            value = transformFromPeriodType(type = Constants.PeriodType.getPeriodType(value)),
            onChange = onChange,
            focusRequester = focusRequester
        )
        DropDownList(
            requestOpen = periodDialogState,
            list = periodList,
            onDialogStateChange,
            onChange
        )
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .clickable(
                    onClick = { onDialogStateChange(true) }
                )
        )
    }
}

@Composable
private fun SubscriptionTypeField(
    modifier: Modifier,
    value: Int,
    subscriptionTypeDialogState: Boolean,
    onDialogStateChange: (Boolean) -> Unit,
    onChange: (type: String) -> Unit,
    focusRequester: FocusRequester
) {
    val subscriptionTypeList = listOf(
        stringResource(id = R.string.music),
        stringResource(id = R.string.entertainment),
        stringResource(id = R.string.online),
        stringResource(id = R.string.video_streaming),
        stringResource(id = R.string.profession),
        stringResource(id = R.string.other)
    )

    Box(
        modifier = modifier
    ) {
        CustomTextField(
            modifier = modifier,
            label = stringResource(id = R.string.subscription_type),
            value = Constants.SubscriptionType.transformFromSubscriptionType(type = Constants.SubscriptionType.getSubscriptionType(value)),
            onChange = onChange,
            focusRequester = focusRequester
        )
        DropDownList(
            requestOpen = subscriptionTypeDialogState,
            list = subscriptionTypeList,
            onDialogStateChange,
            onChange
        )
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .clickable(
                    onClick = { onDialogStateChange(true) }
                )
        )
    }
}

@Composable
private fun AddButton(
    modifier: Modifier,
    click: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary
        ),
        content = { AddButtonView() },
        onClick = click
    )
}

@Composable
private fun AddButtonView() {
    Text(
        modifier = Modifier
            .padding(12.dp),
        text = stringResource(id = R.string.add_subscription),
        style = com.lukakordzaia.core_compose.theme.generalButtonStyle
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
