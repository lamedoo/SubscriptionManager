package com.lukakordzaia.subscriptionmanager.ui.main.subscriptiondetails

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun SubscriptionDetailsScreen(
    subscriptionId: String?
) {
    Toast.makeText(LocalContext.current, subscriptionId, Toast.LENGTH_LONG).show()
}