package com.lukakordzaia.feature_subscription_details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lukakordzaia.core.R
import com.lukakordzaia.core.helpers.DateHelpers
import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.core_compose.custom.LightText

@Composable
fun SubscriptionInfo(
    modifier: Modifier,
    firstPaymentDay: Long?,
    nextPaymentDate: String,
    paymentPeriod: Constants.PeriodType,
    subscriptionType: Constants.SubscriptionType,
    subscriptionPlan: String?
) {
    Column(
        modifier = modifier
    ) {
        LightText(
            modifier = Modifier
                .padding(bottom = 15.dp),
            text = stringResource(id = R.string.subscription_details),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        InfoWrapper(
            firstPaymentDay = firstPaymentDay,
            nextPaymentDate = nextPaymentDate,
            paymentPeriod = paymentPeriod,
            subscriptionType = subscriptionType,
            subscriptionPlan = subscriptionPlan
        )
    }
}

@Composable
private fun InfoWrapper(
    firstPaymentDay: Long?,
    nextPaymentDate: String,
    paymentPeriod: Constants.PeriodType,
    subscriptionType: Constants.SubscriptionType,
    subscriptionPlan: String?
) {
    Column(
        modifier = Modifier
            .background(
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
            shape = MaterialTheme.shapes.medium
            )
    ) {
        InfoItem(icon = R.drawable.icon_repeat_payment, label = R.string.next_payment_day, value = nextPaymentDate)
        Line()
        firstPaymentDay?.let {
            InfoItem(icon = R.drawable.icon_first_payment, label = R.string.first_payment_day, value = DateHelpers.formatDate(it))
            Line()
        }
        InfoItem(icon = R.drawable.icon_subscription_period, label = R.string.payment_period, value = "${Constants.PeriodType.transformFromPeriodType(type = paymentPeriod)}ly")
        Line()
        InfoItem(icon = R.drawable.icon_subscription_plan, label = R.string.plan, value = subscriptionPlan ?: stringResource(id = R.string.not_available))
        Line()
        InfoItem(icon = R.drawable.icon_category, label = R.string.category, value = Constants.SubscriptionType.transformFromSubscriptionType(type = subscriptionType))
    }
}

@Composable
private fun InfoItem(
    icon: Int? = null,
    label: Int,
    value: String
) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let { Icon(
            modifier = Modifier
                .padding(start = 5.dp)
                .width(20.dp)
                .height(20.dp),
            painter = painterResource(id = it),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        }
        LightText(
            modifier = Modifier
                .padding(start = 10.dp),
            text = stringResource(id = label),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )
        LightText(
            modifier = Modifier
                .padding(start = 10.dp),
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun Line() {
    Box(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onSurface)
    )
}

@Preview
@Composable
private fun SubscriptionInfoPreview() {
    SubscriptionInfo(
        modifier = Modifier,
        firstPaymentDay = 120312931203,
        nextPaymentDate = "18.20.21",
        paymentPeriod = Constants.PeriodType.MONTH,
        subscriptionType = Constants.SubscriptionType.MUSIC,
        subscriptionPlan = "Family Plan"
    )
}