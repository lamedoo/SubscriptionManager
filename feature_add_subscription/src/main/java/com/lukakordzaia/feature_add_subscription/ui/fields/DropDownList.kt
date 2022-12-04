package com.lukakordzaia.feature_add_subscription.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DropDownList(
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