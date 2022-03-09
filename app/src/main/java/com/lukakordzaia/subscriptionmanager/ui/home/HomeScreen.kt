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
fun HomeScreen(
    addButtonClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
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
        BottomLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xFF000000))
                .constrainAs(bottomLayout) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(10.dp)
        )
        AddButton(
            modifier = Modifier
                .padding(bottom = 20.dp, end = 10.dp)
                .constrainAs(addButton) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            click = addButtonClick
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

@Composable
fun BottomLayout(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
    }
}

@Composable
fun AddButton(modifier: Modifier, click: () -> Unit) {
    Column(
        modifier = modifier,
    ) {
        Button(
            contentPadding = PaddingValues(20.dp),
            onClick = click,
            shape = Shapes.large,
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add button",
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    HomeScreen {}
}
