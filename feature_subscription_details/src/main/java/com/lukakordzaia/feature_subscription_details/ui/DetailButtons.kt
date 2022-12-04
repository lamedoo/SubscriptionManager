package com.lukakordzaia.feature_subscription_details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lukakordzaia.core.R
import com.lukakordzaia.core_compose.custom.LightText
import com.lukakordzaia.core_compose.theme.Shapes

@Composable
fun DetailButtons(
    modifier: Modifier,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(modifier = modifier) {
        EditButton(onEditClick)
        DeleteButton(onDeleteClick)
    }
}

@Composable
private fun EditButton(
    onEditClick: () -> Unit
) {
    ButtonItem(
        backgroundColor = MaterialTheme.colorScheme.secondary,
        textColor = MaterialTheme.colorScheme.onSecondary,
        text = stringResource(id = R.string.edit_subscription),
        onClick = onEditClick
    )
}

@Composable
private fun DeleteButton(
    onDeleteClick: () -> Unit
) {
    ButtonItem(
        modifier = Modifier.padding(top = 10.dp),
        backgroundColor = MaterialTheme.colorScheme.error,
        textColor = MaterialTheme.colorScheme.onError,
        text = stringResource(id = R.string.delete_subscription),
        onClick = onDeleteClick
    )
}

@Composable
private fun ButtonItem(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    text: String,
    onClick: () -> Unit
) {
    Button(
        shape = Shapes.large,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        LightText(
            modifier = Modifier.padding(10.dp),
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = textColor
        )
    }
}