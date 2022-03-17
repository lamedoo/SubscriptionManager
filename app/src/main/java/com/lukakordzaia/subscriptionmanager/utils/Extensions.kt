package com.lukakordzaia.subscriptionmanager.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lukakordzaia.subscriptionmanager.R
import com.lukakordzaia.subscriptionmanager.ui.theme.mainBold
import com.lukakordzaia.subscriptionmanager.ui.theme.mainLight

@Composable
fun LightText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Light,
    color: Color = MaterialTheme.colors.onPrimary
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = fontSize,
        fontFamily = mainLight,
        fontWeight = fontWeight
    )
}

@Composable
fun BoldText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 12.sp,
    color: Color = MaterialTheme.colors.onPrimary
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = fontSize,
        fontFamily = mainBold,
    )
}

@Composable
fun ProgressDialog(
    showDialog: Boolean,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {},
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment= Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator(
                    strokeWidth = 5.dp,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

@Composable
fun CommonDialog(
    showDialog: Boolean,
    onDismiss: (Boolean) -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss(true) }
        ) {
            Column(
                modifier = Modifier
                    .size(80.dp)
            ) {
                Text(text = stringResource(id = R.string.common_error))
                Button(onClick = { onDismiss(true) }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        }
    }
}