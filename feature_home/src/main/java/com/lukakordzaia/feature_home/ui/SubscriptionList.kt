package com.lukakordzaia.feature_home.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lukakordzaia.feature_home.HomeSubscriptionType
import com.lukakordzaia.feature_home.SubscriptionItem

@Composable
fun SubscriptionList(
    modifier: Modifier,
    subscriptionItems: List<HomeSubscriptionType>,
    onCLick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(subscriptionItems) { item ->
            when (item) {
                is HomeSubscriptionType.Header -> SubscriptionHeaderItem(labelRes = item.label)
                is HomeSubscriptionType.Item -> SubscriptionItem(item = item.data, click = onCLick)
            }
        }
    }
}