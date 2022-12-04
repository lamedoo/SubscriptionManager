package com.lukakordzaia.feature_add_subscription.ui.fields

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.godaddy.android.colorpicker.HsvColor
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import com.lukakordzaia.core.R
import com.lukakordzaia.core_compose.custom.LightText
import com.lukakordzaia.core_compose.theme.fieldLabel

@Composable
fun ColorField(
    modifier: Modifier,
    value: Color?,
    colorDialogState: Boolean,
    onDialogStateChange: (Boolean) -> Unit,
    onChange: (color: Color) -> Unit
) {
    Column(
        modifier = modifier
            .clickable(
                onClick = { onDialogStateChange(true) }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .background(
                    color = value ?: MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.large
                )
                .border(
                    border = BorderStroke(2.dp, value ?: MaterialTheme.colorScheme.primary),
                    shape = MaterialTheme.shapes.large
                )
        ) {
            ColorPickerDialog(
                requestOpen = colorDialogState,
                onDialogStateChange,
                onChange
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 10.dp),
            text = stringResource(id = R.string.color),
            color = fieldLabel
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
                        color = MaterialTheme.colorScheme.secondary,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(10.dp)
            ) {
                HarmonyColorPicker(
                    modifier = Modifier.height(400.dp),
                    harmonyMode = ColorHarmonyMode.SHADES,
                    onColorChanged = { chosenColor.value = it }
                )
                Button(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        onChange(chosenColor.value.toColor())
                        request(false)
                    }
                ) {
                    LightText(text = stringResource(id = R.string.choose_color), fontSize = 14.sp)
                }
            }
        }
    }
}

@Preview
@Composable
fun ColorFieldPreview() {
    ColorField(modifier = Modifier, value = Color.Blue, colorDialogState = false, onDialogStateChange = {}, onChange = {})
}