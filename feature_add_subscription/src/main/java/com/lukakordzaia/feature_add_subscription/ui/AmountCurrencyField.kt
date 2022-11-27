package com.lukakordzaia.feature_add_subscription.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukakordzaia.core.R
import com.lukakordzaia.core.utils.Currencies

@Composable
fun AmountCurrencyField(
    modifier: Modifier,
    amountValue: String,
    onAmountChange: (amount: String) -> Unit,
    amountIsError: Boolean,
    currencyValue: String,
    currencyDialogState: Boolean,
    onCurrencyDialogStateChange: (Boolean) -> Unit,
    onCurrencyChange: (currency: String) -> Unit,
    focusRequester: FocusRequester
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (amount, currency) = createRefs()

        AmountField(
            modifier = Modifier
                .padding(start = 50.dp)
                .constrainAs(amount) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth(),
            value = amountValue,
            onChange = onAmountChange,
            focusRequester = focusRequester,
            isError = amountIsError
        )
        CurrencyField(
            modifier = Modifier
                .constrainAs(currency) {
                    top.linkTo(amount.top)
                    start.linkTo(amount.start)
                }
                .fillMaxWidth(0.19F),
            value = currencyValue,
            currencyDialogState = currencyDialogState,
            onDialogStateChange = onCurrencyDialogStateChange,
            onChange = onCurrencyChange,
            focusRequester = focusRequester
        )
    }
}

@Composable
private fun AmountField(
    modifier: Modifier,
    value: String,
    onChange: (amount: String) -> Unit,
    focusRequester: FocusRequester,
    isError: Boolean
) {
    CustomTextField(
        modifier = modifier,
        label = stringResource(id = R.string.amount),
        value = if (value == "0.0") "" else value,
        onChange = onChange,
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Number,
        focusRequester = focusRequester,
        isError = isError
    )
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
        CustomTextField(
            label = Currencies.Currency.getCurrencyCode(Currencies.Currency.getCurrencySymbol(value)),
            value = Currencies.Currency.getCurrencySymbol(value),
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