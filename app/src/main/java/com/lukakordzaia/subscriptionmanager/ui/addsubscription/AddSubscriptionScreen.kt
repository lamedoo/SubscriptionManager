package com.lukakordzaia.subscriptionmanager.ui.addsubscription

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukakordzaia.subscriptionmanager.helpers.Navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddSubscriptionScreen() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(20.dp)
    ) {
        Text(
            text = "Add New Subscription",
            color = MaterialTheme.colors.onPrimary
        )
    }
}