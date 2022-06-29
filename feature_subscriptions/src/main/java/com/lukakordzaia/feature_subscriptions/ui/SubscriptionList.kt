package com.lukakordzaia.feature_subscriptions.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lukakordzaia.feature_subscriptions.SubscriptionItem
import com.lukakordzaia.feature_subscriptions.SubscriptionListType

@Composable
fun SubscriptionList(
    modifier: Modifier,
    subscriptionItems: List<SubscriptionListType>,
    onCLick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(subscriptionItems) { item ->
            when (item) {
                is SubscriptionListType.Header -> SubscriptionHeaderItem(labelRes = item.label)
                is SubscriptionListType.Item -> SubscriptionItem(item = item.data, click = onCLick)
            }
        }
    }
}