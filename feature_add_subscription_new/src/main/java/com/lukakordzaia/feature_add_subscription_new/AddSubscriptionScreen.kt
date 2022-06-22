package com.lukakordzaia.feature_add_subscription_new

import android.app.DatePickerDialog
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.lukakordzaia.core.R
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.Dimension
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import com.lukakordzaia.core_compose.custom.CommonDialog
import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.core.utils.Constants.PeriodType.Companion.transformFromPeriodType
import com.lukakordzaia.core.utils.Currencies
import com.lukakordzaia.core.utils.LoadingState
import com.lukakordzaia.core_compose.custom.ProgressDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddSubscriptionScreen(
    vm: AddSubscriptionVM,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
) {
    val state = vm.state.collectAsState()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    StateObservers(
        isLoading = state.value.isLoading,
        isUploaded = state.value.isUploaded,
        errorDialogState = state.value.errorDialogIsOpen,
        onDialogStateChange = { value -> vm.setErrorDialogState(value) },
        onDone = {
            scope.launch {
                vm.emptyState()
                bottomSheetState.hide()
            }
        }
    )

    HandleBack(
        state = bottomSheetState
    ) {
        scope.launch {
            focusManager.clearFocus()
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
        val (topTitle, close, link, name, plan, color, amount, period, currency, date, button) = createRefs()

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
                    focusManager.clearFocus()
                    vm.emptyState()
                    bottomSheetState.hide()
                }
            }
        )
        NameField(
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(topTitle.bottom, margin = 30.dp)
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
                    bottom.linkTo(currency.top, margin = 10.dp)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .fillMaxWidth(0.3F),
            value = state.value.colorField ?: MaterialTheme.colors.background,
            colorDialogState = state.value.colorDialogIsOpen,
            onDialogStateChange = { state -> vm.setColorDialogState(state) },
            onChange = { value -> vm.setColor(value) }
        )
        AmountField(
            modifier = Modifier
                .constrainAs(amount) {
                    top.linkTo(plan.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth(0.67F),
            value = state.value.amountField.field,
            onChange = { value -> vm.setAmount(value) },
            focusRequester = focusRequester,
            isError = state.value.amountField.isError
        )
        CurrencyField(
            modifier = Modifier
                .constrainAs(currency) {
                    top.linkTo(amount.top)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(0.3F),
            value = state.value.currencyField,
            currencyDialogState = state.value.currencyDialogIsOpen,
            onDialogStateChange = { state -> vm.setCurrencyDialogState(state) },
            onChange = { value -> vm.setCurrency(value.substring(0, 3)) },
            focusRequester = focusRequester
        )
        PeriodField(
            modifier = Modifier
                .constrainAs(period) {
                    top.linkTo(amount.bottom, margin = 10.dp)
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
            value = state.value.dateField,
            onChange = { value -> vm.setDate(value) },
            focusRequester = focusRequester
        )
        LinkField(
            modifier = Modifier
                .constrainAs(link) {
                    top.linkTo(date.bottom, margin = 10.dp)
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
private fun TopTitle(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.new_sub_title),
        style = com.lukakordzaia.core_compose.theme.titleStyle
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
private fun NameField(
    modifier: Modifier,
    value: String,
    onChange: (name: String) -> Unit,
    focusRequester: FocusRequester,
    isError: Boolean
) {
    AddSubscriptionTextField(
        modifier = modifier,
        label = R.string.name,
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
    AddSubscriptionTextField(
        modifier = modifier,
        label = R.string.plan,
        value = value,
        onChange = onChange,
        focusRequester = focusRequester
    )
}

@Composable
private fun AmountField(
    modifier: Modifier,
    value: String,
    onChange: (amount: String) -> Unit,
    focusRequester: FocusRequester,
    isError: Boolean
) {
    AddSubscriptionTextField(
        modifier = modifier,
        label = R.string.amount,
        value = if (value == "0.0") "" else value,
        onChange = onChange,
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Number,
        focusRequester = focusRequester,
        isError = isError
    )
}

@Composable
private fun LinkField(
    modifier: Modifier,
    value: String,
    onChange: (link: String) -> Unit,
    focusRequester: FocusRequester
) {
    AddSubscriptionTextField(
        modifier = modifier,
        label = R.string.link,
        value = value,
        onChange = onChange,
        focusRequester = focusRequester,
        imeAction = ImeAction.Done
    )
}

@Composable
private fun AddSubscriptionTextField(
    modifier: Modifier = Modifier,
    label: Int,
    value: String,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    focusRequester: FocusRequester,
    onChange: (link: String) -> Unit,
    isError: Boolean = false,
) {
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier
            .focusRequester(focusRequester),
        value = value,
        onValueChange = {
            onChange(it)
        },
        singleLine = true,
        label = { Text(text = stringResource(id = label)) },
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onPrimary,
            backgroundColor = com.lukakordzaia.core_compose.theme._F1F1F5,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.onPrimary,
            errorLabelColor = MaterialTheme.colors.error,
            focusedLabelColor = com.lukakordzaia.core_compose.theme.fieldLabel,
            unfocusedLabelColor = com.lukakordzaia.core_compose.theme.fieldLabel
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) },
            onDone = { focusManager.clearFocus() }
        ),
        isError = isError
    )
}

@Composable
private fun DateField(
    modifier: Modifier,
    value: String,
    onChange: (date: String) -> Unit,
    focusRequester: FocusRequester
) {
    val c = Calendar.getInstance()
    val timePickerDialog = DatePickerDialog(LocalContext.current,
        {_, year: Int, month : Int, day: Int ->
            onChange("$day/$month/$year")
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
    )
    Box(
        modifier = modifier
    ) {
        AddSubscriptionTextField(
            modifier = modifier,
            label = R.string.payment_day,
            value = value,
            onChange = onChange,
            focusRequester = focusRequester
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
private fun CurrencyField(
    modifier: Modifier,
    value: String,
    currencyDialogState: Boolean,
    onDialogStateChange: (Boolean) -> Unit,
    onChange: (currency: String) -> Unit,
    focusRequester: FocusRequester
) {
    Box(modifier = modifier) {
        AddSubscriptionTextField(
            label = R.string.currency,
            value = Currencies.Currency.getCurrencyName(value),
            onChange = onChange,
            focusRequester = focusRequester
        )
        DropDownList(
            requestOpen = currencyDialogState,
            list = Currencies.Currency.getCurrencyList(),
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
        AddSubscriptionTextField(
            modifier = modifier,
            label = R.string.period,
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
private fun DropDownList(
    requestOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    DropdownMenu(
        modifier = Modifier
            .fillMaxWidth(),
        expanded = requestOpen,
        onDismissRequest = { request(false) }
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    request(false)
                    selectedString(it)
                }
            ) {
                Text(text = it, modifier = Modifier.wrapContentWidth())
            }
        }
    }
}

@Composable
private fun ColorField(
    modifier: Modifier,
    value: Color,
    colorDialogState: Boolean,
    onDialogStateChange: (Boolean) -> Unit,
    onChange: (color: Color) -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = value,
                shape = MaterialTheme.shapes.small
            )
            .clickable(
                onClick = { onDialogStateChange(true) }
            )
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp),
            text = stringResource(id = R.string.color),
            color = com.lukakordzaia.core_compose.theme.fieldLabel
        )
        ColorPickerDialog(
            requestOpen = colorDialogState,
            onDialogStateChange,
            onChange
        )
    }
}

@Composable
private fun ColorPickerDialog(
    requestOpen: Boolean,
    request: (Boolean) -> Unit,
    onChange: (color: Color) -> Unit
) {
    val chosenColor = remember { mutableStateOf(HsvColor.Companion.DEFAULT) }

    if (requestOpen) {
        Dialog(
            onDismissRequest = { request(false) }
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(
                        color = MaterialTheme.colors.primaryVariant,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(10.dp)
            ) {
                ClassicColorPicker(
                    modifier = Modifier
                        .height(400.dp),
                    onColorChanged = {
                        chosenColor.value = it
                    }
                )
                Button(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary
                    ),
                    onClick = {
                        onChange(chosenColor.value.toColor())
                        request(false)
                    }
                ) {
                    Text(text = stringResource(id = R.string.choose_color), style = com.lukakordzaia.core_compose.theme.smallButtonStyle)
                }
            }
        }
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

@Composable
private fun StateObservers(
    isLoading: LoadingState?,
    isUploaded: Boolean,
    errorDialogState: Boolean,
    onDialogStateChange: (Boolean) -> Unit,
    onDone: () -> Unit
) {
    when (isLoading) {
        LoadingState.LOADING -> ProgressDialog(showDialog = true)
        LoadingState.LOADED -> {
            ProgressDialog(showDialog = false)
        }
        LoadingState.ERROR -> CommonDialog(showDialog = errorDialogState, onDismiss = { state -> onDialogStateChange(state) })
        else -> {}
    }

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

