package com.lukakordzaia.feature_add_subscription.ui.fields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lukakordzaia.core.R
import com.lukakordzaia.core_compose.custom.LightText
import com.lukakordzaia.core_compose.theme.addSubscriptionTextFieldStyle

@Composable
fun AddSubscriptionField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    focusRequester: FocusRequester,
    onChange: (link: String) -> Unit,
    isError: Boolean = false,
    placeHolder: String = stringResource(id = R.string.enter)
) {
    val focusManager = LocalFocusManager.current

    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LightText(
                modifier = modifier.weight(2F, true),
                text = label,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            TextField(
                modifier = Modifier
                    .weight(2F, true)
                    .focusRequester(focusRequester),
                value = value,
                onValueChange = { onChange(it) },
                singleLine = true,
                placeholder = { LightText(text = placeHolder, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                shape = MaterialTheme.shapes.small,
                textStyle = addSubscriptionTextFieldStyle,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    errorLabelColor = MaterialTheme.colorScheme.error,
                    focusedLabelColor = MaterialTheme.colorScheme.surface,
                    unfocusedLabelColor = MaterialTheme.colorScheme.surface,
                    placeholderColor = if (!isError) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.error
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(MaterialTheme.colorScheme.onSurfaceVariant)
        )
    }
}

@Preview
@Composable
fun CustomTextFieldPreview() {
    AddSubscriptionField(
        label = "very long name",
        value = "",
        focusRequester = FocusRequester(),
        onChange = {},
        isError = false
    )
}