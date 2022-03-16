package com.lukakordzaia.subscriptionmanager.ui.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukakordzaia.subscriptionmanager.R
import com.lukakordzaia.subscriptionmanager.domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.subscriptionmanager.network.LoadingState
import com.lukakordzaia.subscriptionmanager.ui.theme._A6AEC0
import com.lukakordzaia.subscriptionmanager.utils.BoldText
import com.lukakordzaia.subscriptionmanager.utils.LightText
import com.lukakordzaia.subscriptionmanager.utils.ProgressDialog

@Composable
fun HomeScreen(
    vm: HomeVM
) {
    val state = vm.state.collectAsState()

    StateObserver(
        isLoading = state.value.isLoading
    )

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (list, empty) = createRefs()

        SubscriptionList(
            subscriptionItems = state.value.subscriptionItems,
            modifier = Modifier
                .padding(top = 10.dp)
                .constrainAs(list) {
                    top.linkTo(parent.top)
                }
        )

        EmptyList(
            isEmpty = state.value.noSubscriptions,
            modifier = Modifier
                .padding(top = 10.dp)
                .constrainAs(empty) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
private fun SubscriptionList(subscriptionItems: List<SubscriptionItemDomain>, modifier: Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(subscriptionItems) { subscription ->
            SubscriptionItem(item = subscription)
        }
    }
}

@Composable
private fun EmptyList(
    isEmpty: Boolean,
    modifier: Modifier
) {
    if (isEmpty) {
        Column(
            modifier = modifier
        ) {
            LightText(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.no_subscriptions_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = _A6AEC0
            )
            BoldText(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.no_subscriptions_desc),
                fontSize = 24.sp,
                color = _A6AEC0
            )
        }
    }
}

@Composable
private fun StateObserver(
    isLoading: LoadingState?,
) {
    when (isLoading) {
        LoadingState.LOADING -> ProgressDialog(showDialog = true)
        LoadingState.LOADED -> ProgressDialog(showDialog = false)
        LoadingState.ERROR -> {}
        else -> {}
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {

}
