package com.lukakordzaia.feature_add_subscription.ui.fields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lukakordzaia.core_compose.custom.BoldText

@Composable
fun DropDownList(
    requestOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    DropdownMenu(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary),
        expanded = requestOpen,
        onDismissRequest = { request(false) }
    ) {
        list.forEachIndexed { index, s ->
            Column {
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        request(false)
                        selectedString(s)
                    },
                    text = { BoldText(text = s, modifier = Modifier.wrapContentWidth()) }
                )
                if (index != list.size-1) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(MaterialTheme.colorScheme.onPrimary))
                }
            }
        }
    }
}

@Preview
@Composable
private fun DropDownListPreview() {
    DropDownList(list = emptyList(), request = {}, selectedString = {})
}