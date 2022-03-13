package com.lukakordzaia.subscriptionmanager.ui.addsubscription

import android.app.DatePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukakordzaia.subscriptionmanager.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.Dimension
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import com.lukakordzaia.subscriptionmanager.ui.theme.*
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
            value = state.value.nameField,
            onChange = { value -> vm.setName(value) },
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
            value = state.value.colorField?.toColor() ?: MaterialTheme.colors.background,
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
            value = state.value.amountField,
            onChange = { value -> vm.setAmount(value) },
        )
        CurrencyField(
            modifier = Modifier
                .constrainAs(currency) {
                    top.linkTo(amount.top)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(0.3F),
            value = state.value.currencyField,
            onChange = { value -> vm.setCurrency(value) }
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
            onChange = { value -> vm.setPeriod(value) },
        )
        DateField(
            modifier = Modifier
                .constrainAs(date) {
                    top.linkTo(period.bottom, margin = 10.dp)
                }
                .fillMaxWidth(),
            value = state.value.dateField,
            onChange = { value -> vm.setDate(value) },
        )
        LinkField(
            modifier = Modifier
                .constrainAs(link) {
                    top.linkTo(date.bottom, margin = 10.dp)
                }
                .fillMaxWidth(),
            value = state.value.linkField,
            onChange = { value -> vm.setLink(value) },
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
    AddSubscriptionTextField(
        modifier = modifier,
        label = R.string.link,
        value = value,
        onChange = onChange
    )
}

@Composable
private fun NameField(
    modifier: Modifier,
    value: String,
    onChange: (name: String) -> Unit
) {
    AddSubscriptionTextField(
        modifier = modifier,
        label = R.string.name,
        value = value,
        onChange = onChange,
    )
}

@Composable
private fun PlanField(
    modifier: Modifier,
    value: String,
    onChange: (plan: String) -> Unit
) {
    AddSubscriptionTextField(
        modifier = modifier,
        label = R.string.plan,
        value = value,
        onChange = onChange,
    )
}

@Composable
private fun AmountField(
    modifier: Modifier,
    value: String,
    onChange: (amount: String) -> Unit
) {
    AddSubscriptionTextField(
        modifier = modifier,
        label = R.string.amount,
        value = if (value == "0.0") "" else value,
        onChange = onChange,
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Number
    )
}

@Composable
private fun CurrencyField(
    modifier: Modifier,
    value: String,
    onChange: (currency: String) -> Unit
) {
    AddSubscriptionTextField(
        modifier = modifier,
        label = R.string.currency,
        value = value,
        onChange = onChange
    )
}

@Composable
private fun AddSubscriptionTextField(
    modifier: Modifier,
    label: Int,
    value: String,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    focusRequester: FocusRequester = remember { FocusRequester() },
    onChange: (link: String) -> Unit
) {
    TextField(
        modifier = modifier
            .focusRequester(focusRequester),
        value = value,
        onValueChange = {
            onChange(it)
        },
        singleLine = true,
        label = { Text(text = stringResource(id = label), color = fieldLabel) },
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onPrimary,
            backgroundColor = _F1F1F5,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.onPrimary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        )
    )
}

@Composable
private fun DateField(
    modifier: Modifier,
    value: String,
    onChange: (date: String) -> Unit
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
            label = R.string.date,
            value = value,
            onChange = onChange
        )
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { timePickerDialog.show() }
                )
        )
    }
}

@Composable
private fun PeriodField(
    modifier: Modifier,
    value: String,
    periodDialogState: Boolean,
    onDialogStateChange: (Boolean) -> Unit,
    onChange: (period: String) -> Unit
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
            value = value,
            onChange = onChange
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
    onChange: (color: HsvColor) -> Unit
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
            color = fieldLabel
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
    onChange: (color: HsvColor) -> Unit
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
                        onChange(chosenColor.value)
                        request(false)
                    }
                ) {
                    Text(text = stringResource(id = R.string.choose_color), style = smallButtonStyle)
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
        style = generalButtonStyle
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