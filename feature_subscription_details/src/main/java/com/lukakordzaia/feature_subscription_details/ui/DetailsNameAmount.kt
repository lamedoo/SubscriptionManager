package com.lukakordzaia.feature_subscription_details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.core.utils.Currencies
import com.lukakordzaia.core_compose.custom.BoldText
import com.lukakordzaia.core_compose.custom.LightText

@Composable
fun DetailNameAmount(
    modifier: Modifier,
    detailName: String,
    detailCurrency: String,
    detailAmount: Double,
    detailColor: Color?,
    detailPeriod: Constants.PeriodType
) {
    ConstraintLayout(
        modifier = modifier
            .padding(20.dp)
    ) {
        val (name, amount, line) = createRefs()

        DetailName(
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            name = detailName
        )
        DetailAmount(
            modifier = Modifier
                .constrainAs(amount) {
                    top.linkTo(name.bottom, 15.dp)
                    start.linkTo(name.start)
                    end.linkTo(name.end)
                },
            currency = detailCurrency,
            amount = detailAmount.toString(),
            period = detailPeriod
        )
        Line(
            modifier = Modifier
                .constrainAs(line) {
                    top.linkTo(amount.bottom, 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            color = detailColor
        )
    }
}

@Composable
private fun DetailName(
    modifier: Modifier,
    name: String
) {
    BoldText(
        modifier = modifier,
        text = name,
        fontSize = 25.sp
    )
}

@Composable
private fun DetailAmount(
    modifier: Modifier,
    currency: String,
    amount: String,
    period: Constants.PeriodType
) {
    LightText(
        modifier = modifier,
        text = "${Currencies.Currency.getCurrencySymbol(currency)}$amount / ${Constants.PeriodType.transformFromPeriodType(period)}",
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun Line(
    modifier: Modifier,
    color: Color?
) {
    Box(
        modifier = modifier
            .width(50.dp)
            .height(2.dp)
            .background(color = color ?: MaterialTheme.colors.secondary)
    )
}

@Preview
@Composable
private fun DetailNameAmountPreview() {
    DetailNameAmount(
        modifier = Modifier,
        detailName = "Spotify",
        detailCurrency = "USD",
        detailAmount = 14.99,
        detailPeriod = Constants.PeriodType.MONTH,
        detailColor = MaterialTheme.colors.secondary
    )
}