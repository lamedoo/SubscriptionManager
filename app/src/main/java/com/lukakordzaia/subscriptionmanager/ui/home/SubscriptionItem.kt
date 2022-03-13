package com.lukakordzaia.subscriptionmanager.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukakordzaia.subscriptionmanager.domain.domainmodels.SubscriptionDomain
import com.lukakordzaia.subscriptionmanager.ui.theme.Shapes
import com.lukakordzaia.subscriptionmanager.ui.theme._FFFFFF
import com.lukakordzaia.subscriptionmanager.utils.BoldText
import com.lukakordzaia.subscriptionmanager.utils.Constants
import com.lukakordzaia.subscriptionmanager.utils.LightText

@Composable
fun SubscriptionItem(item: SubscriptionDomain) {
    ItemWrapper(
        itemColor = item.color,
        itemName = item.name,
        itemPlan = item.plan,
        itemCurrency = item.currency,
        itemAmount = item.amount,
        itemPeriod = item.periodType
    )
}

@Composable
private fun ItemWrapper(
    itemColor: Color,
    itemName: String,
    itemPlan: String,
    itemCurrency: String,
    itemAmount: Double,
    itemPeriod: String
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
            .shadow(
                elevation = 2.dp,
                shape = MaterialTheme.shapes.medium
            )
            .background(
                color = _FFFFFF,
                shape = MaterialTheme.shapes.medium
            )
            .padding(10.dp)
    ) {
        val (name, description, amount, period) = createRefs()
        
        ItemName(
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            name = itemName,
            nameColor = itemColor
        )
        ItemPlan(
            modifier = Modifier
                .constrainAs(description) {
                    top.linkTo(name.bottom, margin = 5.dp)
                    start.linkTo(name.start)
                },
            plan = itemPlan
        )
        ItemAmount(
            modifier = Modifier
                .constrainAs(amount) {
                    top.linkTo(parent.top)
                    bottom.linkTo(period.top)
                    end.linkTo(parent.end)
                },
            currency = itemCurrency,
            amount = itemAmount.toString()
        )
        ItemPeriod(
            modifier = Modifier
                .constrainAs(period) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(amount.end)
                    start.linkTo(amount.start)
                    top.linkTo(amount.bottom)
                },
            period = itemPeriod
        )

        createVerticalChain(
            amount, period,
            chainStyle = ChainStyle.Packed
        )
    }
}

@Composable
private fun ItemName(
    modifier: Modifier,
    name: String,
    nameColor: Color
) {
    BoldText(
        modifier = modifier,
        text = name,
        color = nameColor,
        fontSize = 18.sp
    )
}

@Composable
private fun ItemPlan(
    modifier: Modifier,
    plan: String
) {
    LightText(
        modifier = modifier,
        text = plan,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ItemAmount(
    modifier: Modifier,
    currency: String,
    amount: String
) {
    BoldText(
        modifier = modifier,
        text = "$currency $amount",
        fontSize = 15.sp
    )
}

@Composable
private fun ItemPeriod(
    modifier: Modifier,
    period: String
) {
    LightText(
        modifier = modifier,
        text = period,
        fontWeight = FontWeight.Bold
    )
}

@Preview
@Composable
fun SubscriptionItemPreview() {
    ItemWrapper(
        itemColor = Color(0xFFFFFFFF),
        itemName = "Spotify",
        itemPlan = "Family Plan",
        itemCurrency = "USD",
        itemAmount = 14.99,
        itemPeriod = "Month"
    )
}