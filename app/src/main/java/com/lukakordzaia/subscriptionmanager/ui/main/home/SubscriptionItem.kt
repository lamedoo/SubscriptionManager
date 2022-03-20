package com.lukakordzaia.subscriptionmanager.ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukakordzaia.subscriptionmanager.customcomposables.BoldText
import com.lukakordzaia.subscriptionmanager.customcomposables.LightText
import com.lukakordzaia.subscriptionmanager.domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.subscriptionmanager.ui.theme._FFFFFF
import com.lukakordzaia.subscriptionmanager.utils.*
import com.lukakordzaia.subscriptionmanager.utils.Constants.PeriodType.Companion.transformFromPeriodType

@Composable
fun SubscriptionItem(item: SubscriptionItemDomain, click: (String) -> Unit) {
    val subscription = item.toJson()

    ItemWrapper(
        item = item,
        itemColor = item.color,
        itemName = item.name,
        itemPlan = item.plan!!,
        itemCurrency = item.currency,
        itemAmount = item.amount,
        itemPeriod = item.periodType,
        click = { subscription?.let { click(subscription) } }
    )
}

@Composable
private fun ItemWrapper(
    item: SubscriptionItemDomain,
    itemColor: Color?,
    itemName: String,
    itemPlan: String,
    itemCurrency: String,
    itemAmount: Double,
    itemPeriod: Constants.PeriodType,
    click: () -> Unit?
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
            .shadow(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.medium
            )
            .background(
                color = _FFFFFF,
                shape = MaterialTheme.shapes.medium
            )
            .padding(10.dp)
            .clickable {
                click.invoke()
            }
    ) {
        val (name, description, amount, period) = createRefs()
        
        ItemName(
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            name = itemName,
            nameColor = itemColor ?: MaterialTheme.colors.onPrimary
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
        text = "${Currencies.Currency.getCurrencySymbol(currency)} $amount",
        fontSize = 15.sp
    )
}

@Composable
private fun ItemPeriod(
    modifier: Modifier,
    period: Constants.PeriodType
) {
    LightText(
        modifier = modifier,
        text = transformFromPeriodType(type = period),
        fontWeight = FontWeight.Bold
    )
}

//@Preview
//@Composable
//fun SubscriptionItemPreview() {
//    ItemWrapper(
//        itemId = "123",
//        itemColor = Color(0xFFFFFFFF),
//        itemName = "Spotify",
//        itemPlan = "Family Plan",
//        itemCurrency = "USD",
//        itemAmount = 14.99,
//        itemPeriod = Constants.PeriodType.MONTH,
//        click = { "123" }
//    )
//}