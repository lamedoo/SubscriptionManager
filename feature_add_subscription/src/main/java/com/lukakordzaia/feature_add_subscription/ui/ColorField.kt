package com.lukakordzaia.feature_add_subscription.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import com.lukakordzaia.core.R
import com.lukakordzaia.core_compose.theme.fieldLabel
import com.lukakordzaia.core_compose.theme.smallButtonStyle

@Composable
fun ColorField(
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
    onChange: (color: Color) -> Unit
) {
    val chosenColor = remember { mutableStateOf(HsvColor.DEFAULT) }

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
                    Text(text = stringResource(id = R.string.choose_color), style = smallButtonStyle)
                }
            }
        }
    }
}