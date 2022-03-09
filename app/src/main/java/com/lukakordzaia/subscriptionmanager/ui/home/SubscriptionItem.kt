package com.lukakordzaia.subscriptionmanager.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukakordzaia.subscriptionmanager.domain.domainmodels.SubscriptionDomain
import com.lukakordzaia.subscriptionmanager.ui.theme.Shapes
import com.lukakordzaia.subscriptionmanager.utils.Constants

@Composable
fun SubscriptionItem(item: SubscriptionDomain) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
            .border(
                width = 3.dp,
                color = item.color,
                shape = Shapes.medium
            )
            .padding(10.dp)
    ) {
        val (name, description, amount) = createRefs()
        
        Text(
            text = item.name,
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = item.description,
            modifier = Modifier
                .constrainAs(description) {
                    top.linkTo(name.bottom, margin = 5.dp)
                    start.linkTo(name.start)
                }
            )
        Column(
            modifier = Modifier
                .constrainAs(amount) {
                    top.linkTo(name.top)
                    bottom.linkTo(description.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = item.amount.toString(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = item.currency,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun SubscriptionItemPreview() {
    SubscriptionItem(item = SubscriptionDomain(
        subscriptionType = "Spotify",
        name = "Spotify",
        description = "Family Plan",
        color = Color(0xFF1DB954),
        currency = "USD",
        amount = 14.99,
        periodType = Constants.PeriodType.MONTH
    ))
}