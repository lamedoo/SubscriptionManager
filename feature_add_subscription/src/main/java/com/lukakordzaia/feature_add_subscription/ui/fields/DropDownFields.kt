package com.lukakordzaia.feature_add_subscription.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.lukakordzaia.core.R
import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.feature_add_subscription.ui.fields.AddSubscriptionField

@Composable
internal fun PeriodField(
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

    Box {
        AddSubscriptionField(
            label = stringResource(id = R.string.payment_interval),
            value = Constants.PeriodType.transformFromPeriodType(type = Constants.PeriodType.getPeriodType(value)),
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
internal fun SubscriptionTypeField(
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

    Box {
        AddSubscriptionField(
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
