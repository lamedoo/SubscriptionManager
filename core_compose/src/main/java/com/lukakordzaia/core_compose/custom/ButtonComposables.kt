package com.lukakordzaia.core_compose.custom

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.lukakordzaia.core_compose.theme.Shapes

@Composable
fun GeneralTextButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onPrimary,
    text: String,
    onClick: () -> Unit
) {
    Button(
        shape = Shapes.large,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = textColor
        ),
        modifier = modifier,
        onClick = onClick,
    ) {
        LightText(
            text = text,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}