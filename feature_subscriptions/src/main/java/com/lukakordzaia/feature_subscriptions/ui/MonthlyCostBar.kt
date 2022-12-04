package com.lukakordzaia.feature_subscriptions.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukakordzaia.core.R
import com.lukakordzaia.core.utils.Currencies
import com.lukakordzaia.core_compose.custom.BoldText
import com.lukakordzaia.core_compose.custom.GeneralTextButton
import com.lukakordzaia.core_compose.custom.LightText
import com.lukakordzaia.core_compose.theme.Shapes

@Composable
fun MonthlyCostBar(
    modifier: Modifier = Modifier,
    totalBalance: Double,
    onMoreClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
                shape = Shapes.small
            )
            .padding(20.dp)
    ) {
        val (title, amount, more) = createRefs()

        Title(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Amount(
            modifier = Modifier
                .constrainAs(amount) {
                    top.linkTo(title.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                },
            monthlyAmount = totalBalance
        )
        MoreButton(
            modifier = Modifier
                .constrainAs(more) {
                    end.linkTo(parent.end)
                    top.linkTo(amount.top)
                    bottom.linkTo(amount.bottom)
                },
            onClick = onMoreClick
        )
    }
}

@Composable
private fun Title(
    modifier: Modifier
) {
    LightText(
        modifier = modifier,
        text = stringResource(id = R.string.this_month_expense),
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun Amount(
    modifier: Modifier,
    monthlyAmount: Double,
    currency: String = Currencies.Currency.USD.symbol
) {
    BoldText(
        modifier = modifier,
        text = "$currency $monthlyAmount",
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun MoreButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    GeneralTextButton(
        modifier = modifier,
        onClick = onClick,
        text = stringResource(id = R.string.in_details)
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun MonthlyCostPreview() {
    MonthlyCostBar(modifier = Modifier, totalBalance = 248.0) {}
}
