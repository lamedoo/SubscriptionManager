package com.lukakordzaia.subscriptionmanager.ui.navigation.subscriptiondetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.lukakordzaia.subscriptionmanager.customcomposables.BoldText

@Composable
fun SubscriptionDetailsScreen(
    subscription: com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain,
    navController: NavHostController,
    vm: SubscriptionDetailsVM
) {
    val state = vm.state.collectAsState()
    vm.getSubscriptionDetails(subscription)

    state.value.details?.let { details ->
        DetailsWrapper(
            detailName = details.name,
            click = { navController.popBackStack() }
        )
    }
}

@Composable
private fun DetailsWrapper(
    detailName: String,
    click: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .shadow(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.medium
            )
            .background(
                color = com.lukakordzaia.core_compose.theme._FFFFFF,
                shape = MaterialTheme.shapes.medium
            )
            .padding(10.dp)
    ) {
        val (topBar, name) = createRefs()

        TopBar(
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            click = click
        )

        DetailName(
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(topBar.bottom)
                    start.linkTo(parent.start)
                },
            name = detailName
        )
    }
}

@Composable
private fun TopBar(
    modifier: Modifier,
    click: () -> Unit
) {
    Icon(
        modifier = modifier
            .clickable { click.invoke() }
            .padding(10.dp),
        imageVector = Icons.Filled.ArrowBack,
        contentDescription = null
    )
}

@Composable
private fun DetailName(
    modifier: Modifier,
    name: String
) {
    BoldText(
        modifier = modifier,
        text = name,
        fontSize = 48.sp
    )
}