package com.lukakordzaia.feature_add_subscription.ui

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.lukakordzaia.core.R
import com.lukakordzaia.core.utils.Currencies
import com.lukakordzaia.feature_add_subscription.ui.fields.AddSubscriptionField
import com.lukakordzaia.feature_add_subscription.ui.fields.DropDownList
import java.util.Calendar

@Composable
internal fun NameField(
    value: String,
    onChange: (name: String) -> Unit,
    focusRequester: FocusRequester,
    isError: Boolean
) {
    AddSubscriptionField(
        label = stringResource(id = R.string.name),
        value = value,
        onChange = onChange,
        focusRequester = focusRequester,
        isError = isError
    )
}

@Composable
internal fun PlanField(
    value: String,
    onChange: (plan: String) -> Unit,
    focusRequester: FocusRequester
) {
    AddSubscriptionField(
        label = stringResource(id = R.string.plan),
        value = value,
        onChange = onChange,
        focusRequester = focusRequester
    )
}

@Composable
internal fun CurrencyField(
    value: String,
    currencyDialogState: Boolean,
    onDialogStateChange: (Boolean) -> Unit,
    onChange: (currency: String) -> Unit,
    focusRequester: FocusRequester
) {
    Box {
        AddSubscriptionField(
            label = stringResource(id = R.string.currency),
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
internal fun AmountField(
    value: String,
    onChange: (amount: String) -> Unit,
    focusRequester: FocusRequester,
    isError: Boolean
) {
    AddSubscriptionField(
        label = stringResource(id = R.string.amount),
        value = if (value == "0.0") "" else value,
        onChange = onChange,
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Number,
        focusRequester = focusRequester,
        isError = isError,
        placeHolder = "0.00"
    )
}


@Composable
internal fun LinkField(
    value: String,
    onChange: (link: String) -> Unit,
    focusRequester: FocusRequester
) {
    AddSubscriptionField(
        label = stringResource(id = R.string.link),
        value = value,
        onChange = onChange,
        focusRequester = focusRequester,
        imeAction = ImeAction.Done
    )
}

@Composable
internal fun DateField(
    value: String,
    onChange: (date: String) -> Unit,
    focusRequester: FocusRequester,
    isError: Boolean
) {
    val c = Calendar.getInstance()
    val timePickerDialog = DatePickerDialog(
        LocalContext.current,
        {_, year: Int, month : Int, day: Int ->
            onChange("$day/${month + 1}/$year")
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
    )
    Box {
        AddSubscriptionField(
            label = stringResource(id = R.string.first_payment_day),
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