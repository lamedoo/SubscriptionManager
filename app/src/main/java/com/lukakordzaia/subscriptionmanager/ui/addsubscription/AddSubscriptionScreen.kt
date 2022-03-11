package com.lukakordzaia.subscriptionmanager.ui.addsubscription

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukakordzaia.subscriptionmanager.R
import com.lukakordzaia.subscriptionmanager.ui.theme.titleStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import com.lukakordzaia.subscriptionmanager.ui.theme._A6AEC0
import com.lukakordzaia.subscriptionmanager.ui.theme._F1F1F5
import com.lukakordzaia.subscriptionmanager.utils.hideKeyboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddSubscriptionScreen(
    vm: AddSubscriptionVM,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
) {
    val state = vm.state.collectAsState()

    if (!state.value.keyboardIsVisible) {
        hideKeyboard()
    }

    HandleBack(
        state = bottomSheetState
    ) {
        scope.launch {
            vm.emptyState()
            bottomSheetState.hide()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val (topTitle, close, link, name, plan, amount, period, currency, date) = createRefs()

        TopTitle(
            modifier = Modifier
                .constrainAs(topTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        CloseButton(
            modifier = Modifier
                .constrainAs(close) {
                    top.linkTo(topTitle.top)
                    bottom.linkTo(topTitle.bottom)
                    end.linkTo(parent.end)
                },
            close = {
                scope.launch {
                    vm.emptyState()
                    bottomSheetState.hide()
                }
            }
        )
        LinkField(
            modifier = Modifier
                .constrainAs(link) {
                    top.linkTo(topTitle.bottom, margin = 30.dp)
                }
                .fillMaxWidth(),
            value = state.value.linkField,
            onChange = { value -> vm.setLink(value) }
        )
        NameField(
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(link.bottom, margin = 10.dp)
                }
                .fillMaxWidth(),
            value = state.value.nameField,
            onChange = { value -> vm.setName(value) }
        )
        PlanField(
            modifier = Modifier
                .constrainAs(plan) {
                    top.linkTo(name.bottom, margin = 10.dp)
                }
                .fillMaxWidth(),
            value = state.value.planField,
            onChange = { value -> vm.setPlan(value) }
        )
        AmountField(
            modifier = Modifier
                .constrainAs(amount) {
                    top.linkTo(plan.bottom, margin = 10.dp)
                }
                .fillMaxWidth(),
            value = state.value.amountField.toString(),
            onChange = { value -> vm.setAmount(value) }
        )
        PeriodField(
            modifier = Modifier
                .constrainAs(period) {
                    top.linkTo(amount.bottom, margin = 10.dp)
                }
                .fillMaxWidth(),
            value = state.value.periodField.toString(),
            onChange = { value -> vm.setPeriod(value) }
        )
        DateField(
            modifier = Modifier
                .constrainAs(date) {
                    top.linkTo(period.bottom, margin = 10.dp)
                }
                .fillMaxWidth(),
            value = state.value.dateField,
            onChange = { value -> vm.setDate(value) }
        )
    }
}

@Composable
private fun TopTitle(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.new_sub_title),
        style = titleStyle
    )
}

@Composable
private fun CloseButton(
    modifier: Modifier,
    close: () -> Unit
) {
    IconButton(
        modifier = modifier,
        content = {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
            )
        },
        onClick = close
    )
}

@Composable
private fun LinkField(
    modifier: Modifier,
    value: String,
    onChange: (link: String) -> Unit
) {
    AddSubscriptionTextField(modifier = modifier, label = R.string.link, value = value, onChange = onChange)
}

@Composable
private fun NameField(
    modifier: Modifier,
    value: String,
    onChange: (name: String) -> Unit
) {
    AddSubscriptionTextField(modifier = modifier, label = R.string.name, value = value, onChange = onChange)
}

@Composable
private fun PlanField(
    modifier: Modifier,
    value: String,
    onChange: (plan: String) -> Unit
) {
    AddSubscriptionTextField(modifier = modifier, label = R.string.plan, value = value, onChange = onChange)
}

@Composable
private fun AmountField(
    modifier: Modifier,
    value: String,
    onChange: (amount: String) -> Unit
) {
    AddSubscriptionTextField(modifier = modifier, label = R.string.amount, value = value, onChange = onChange)
}

@Composable
private fun PeriodField(
    modifier: Modifier,
    value: String,
    onChange: (period: String) -> Unit
) {
    AddSubscriptionTextField(modifier = modifier, label = R.string.period, value = value, onChange = onChange)
}

@Composable
private fun CurrencyField(
    modifier: Modifier,
    value: String,
    onChange: (currency: String) -> Unit
) {
    AddSubscriptionTextField(modifier = modifier, label = R.string.currency, value = value, onChange = onChange)
}

@Composable
private fun DateField(
    modifier: Modifier,
    value: String,
    onChange: (date: String) -> Unit
) {
    AddSubscriptionTextField(modifier = modifier, label = R.string.date, value = value, onChange = onChange)
}



@Composable
private fun AddSubscriptionTextField(
    modifier: Modifier,
    label: Int,
    value: String,
    onChange: (link: String) -> Unit
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            onChange(it)
        },
        singleLine = true,
        label = { Text(text = stringResource(id = label), color = _A6AEC0) },
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onPrimary,
            backgroundColor = _F1F1F5,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.onPrimary
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HandleBack(
    state: ModalBottomSheetState,
    onBack: () -> Unit
) {
    BackHandler(enabled = state.isVisible) {
        onBack.invoke()
    }
}