package com.lukakordzaia.feature_subscriptions.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.core.utils.Constants.PeriodType.Companion.transformFromPeriodType
import com.lukakordzaia.core.utils.Currencies
import com.lukakordzaia.core.utils.toJson
import com.lukakordzaia.core_compose.custom.BoldText
import com.lukakordzaia.core_compose.custom.LightText

@Composable
fun SubscriptionItem(item: com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain, click: (String) -> Unit) {
    val subscription = item.toJson()

    ItemWrapper(
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
            .padding(start = 15.dp, end = 20.dp, bottom = 10.dp)
            .shadow(
                elevation = 2.dp,
                shape = MaterialTheme.shapes.small
            )
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
            .padding(top = 15.dp, bottom = 15.dp, end = 15.dp)
            .clickable {
                click.invoke()
            }
    ) {
        val (
            line,
            name,
            description,
            amount,
            period
        ) = createRefs()

        ColoredLine(
            modifier = Modifier
                .constrainAs(line) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    height = Dimension.fillToConstraints
                },
            color = itemColor
        )
        ItemName(
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(line.end, 10.dp)
                    end.linkTo(amount.start, 10.dp)
                    width = Dimension.fillToConstraints
                },
            name = itemName
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
private fun ColoredLine(
    modifier: Modifier,
    color: Color?
) {
    Box(
        modifier = modifier
            .width(2.dp)
            .background(color = color ?: MaterialTheme.colorScheme.secondary)
    )
}

@Composable
private fun ItemName(
    modifier: Modifier,
    name: String,
) {
    BoldText(
        modifier = modifier,
        text = name,
        fontSize = 20.sp
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
    LightText(
        modifier = modifier,
        text = "${Currencies.Currency.getCurrencySymbol(currency)}$amount",
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold
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
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.inversePrimary
    )
}

@Preview
@Composable
fun SubscriptionItemPreview() {
    ItemWrapper(
        itemColor = MaterialTheme.colorScheme.onPrimary,
        itemName = "Spotify",
        itemPlan = "Family Plan",
        itemCurrency = "USD",
        itemAmount = 14.99,
        itemPeriod = Constants.PeriodType.MONTH,
        click = {}
    )
}