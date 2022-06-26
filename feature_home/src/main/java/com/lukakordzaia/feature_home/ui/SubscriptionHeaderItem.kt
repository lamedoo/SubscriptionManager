package com.lukakordzaia.feature_home.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lukakordzaia.core.R
import com.lukakordzaia.core_compose.custom.BoldText

@Composable
fun SubscriptionHeaderItem(
    labelRes: Int
) {
    BoldText(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 25.dp, bottom = 15.dp),
        text = stringResource(id = labelRes),
        fontSize = 16.sp
    )
}

@Preview
@Composable
private fun SubscriptionHeaderItemPreview() {
    SubscriptionHeaderItem(labelRes = R.string.profession)
}