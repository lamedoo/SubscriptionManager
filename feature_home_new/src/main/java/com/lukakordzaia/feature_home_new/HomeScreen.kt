package com.lukakordzaia.feature_home_new

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.lukakordzaia.core.utils.LoadingState
import com.lukakordzaia.core.R
import com.lukakordzaia.subscriptionmanager.customcomposables.BoldText
import com.lukakordzaia.core.utils.Currencies
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.subscriptionmanager.customcomposables.LightText
import com.lukakordzaia.core_compose.custom.ProgressDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    navController: NavHostController,
    vm: HomeVM
) {
    val state = vm.state.collectAsState()

    InitObserver(
        isLoading = state.value.isLoading,
        navController = navController,
        singleEvent = vm.singleEvent
    )

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (list, empty) = createRefs()

        CollapsingHomeScreen(
            modifier = Modifier
                .padding(top = 10.dp)
                .constrainAs(list) {
                    top.linkTo(parent.top)
                },
            subscriptionItems = state.value.subscriptionItems,
            scrollOffset = state.value.scrollOffset,
            changeScrollOffset = { index -> vm.setScrollOffset(index) },
            click = { subscription -> vm.navigateToDetails(subscription) }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollapsingHomeScreen(
    modifier: Modifier,
    subscriptionItems: List<SubscriptionItemDomain>,
    scrollOffset: Float,
    changeScrollOffset: (Int) -> Unit,
    click: (String) -> Unit
){
    if (subscriptionItems.isNotEmpty()) {
        val scrollState = rememberLazyListState()
        changeScrollOffset(scrollState.firstVisibleItemIndex)

        Column(
            modifier = modifier
        ) {
            TopBar(scrollOffset = scrollOffset)
            Spacer(modifier = Modifier.height(2.dp))
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .background(color = com.lukakordzaia.core_compose.theme._FFFFFF, shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .fillMaxHeight()
            ) {
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .background(color = com.lukakordzaia.core_compose.theme._FFFFFF, shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                            .padding(bottom = 20.dp)
                            .fillMaxWidth()
                    ) {
                        Spacer(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .width(100.dp)
                                .height(3.dp)
                                .background(com.lukakordzaia.core_compose.theme._C4C9D7)
                        )
                    }
                }
                items(subscriptionItems) { subscription ->
                    SubscriptionItem(item = subscription, click)
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    monthlyAmount: Double = 248.22,
    currency: String = Currencies.Currency.USD.symbol,
    scrollOffset: Float
) {
    val barSize by animateDpAsState(targetValue = max(100.dp, 150.dp + (scrollOffset.dp * 50F) ))

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(max(100.dp, barSize))
            .background(MaterialTheme.colors.background)
    ) {
        val (month, amount, account, search) = createRefs()

        val monthFormat = SimpleDateFormat("MMMM")

        LightText(
            modifier = Modifier
                .constrainAs(month) {
                    bottom.linkTo(amount.top)
                    start.linkTo(amount.start)
                    end.linkTo(amount.end)
                },
            text = monthFormat.format(Calendar.getInstance().time),
            color = com.lukakordzaia.core_compose.theme._A6AEC0,
            fontSize = 14.sp
        )
        BoldText(
            modifier = Modifier
                .constrainAs(amount) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = " $currency$monthlyAmount",
            color = MaterialTheme.colors.secondary,
            fontSize = 55.sp,
        )
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
                color = com.lukakordzaia.core_compose.theme._A6AEC0
            )
            BoldText(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.no_subscriptions_desc),
                fontSize = 24.sp,
                color = com.lukakordzaia.core_compose.theme._A6AEC0
            )
        }
    }
}

@Composable
private fun InitObserver(
    isLoading: LoadingState,
    navController: NavHostController,
    singleEvent: Flow<com.lukakordzaia.core.helpers.SingleEvent>
) {

    when (isLoading) {
        LoadingState.LOADING -> ProgressDialog(showDialog = true)
        LoadingState.LOADED -> ProgressDialog(showDialog = false)
        LoadingState.ERROR -> {}
    }

    LaunchedEffect(key1 = null) {
        singleEvent.collectLatest {
            when (it) {
                is com.lukakordzaia.core.helpers.SingleEvent.Navigation -> {
                    navController.navigate(it.destination)
                }
            }
        }
    }
}