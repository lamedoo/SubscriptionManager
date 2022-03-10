package com.lukakordzaia.subscriptionmanager.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukakordzaia.subscriptionmanager.domain.domainmodels.SubscriptionDomain
import com.lukakordzaia.subscriptionmanager.ui.theme.Shapes
import com.lukakordzaia.subscriptionmanager.utils.Constants

@Composable
fun HomeScreen(vm: HomeViewModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (list, bottomLayout, addButton) = createRefs()

        SubscriptionList(
            subscriptions = listOf(SubscriptionDomain(
            subscriptionType = "Spotify",
            name = "Spotify",
            description = "Family Plan",
            color = Color(0xFF1DB954),
            currency = "USD",
            amount = 14.99,
            periodType = Constants.PeriodType.MONTH
        )),
            modifier = Modifier
                .padding(top = 10.dp)
                .constrainAs(list) {
                    top.linkTo(parent.top)
                }
        )
    }
}

@Composable
fun SubscriptionList(subscriptions: List<SubscriptionDomain>, modifier: Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(subscriptions) { subscription ->
            SubscriptionItem(item = subscription)
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {

}
