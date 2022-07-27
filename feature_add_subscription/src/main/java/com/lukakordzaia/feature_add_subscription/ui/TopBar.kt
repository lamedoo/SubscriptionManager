package com.lukakordzaia.feature_add_subscription.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukakordzaia.core.R
import com.lukakordzaia.core_compose.theme.titleStyle

@Composable
fun TopBar(
    modifier: Modifier,
    onClose: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (topTitle, closeButton) = createRefs()

        TopTitle(
            modifier = Modifier
                .constrainAs(topTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        CloseButton(
            modifier = Modifier
                .constrainAs(closeButton) {
                    top.linkTo(topTitle.top)
                    bottom.linkTo(topTitle.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            onClose.invoke()
        }
    }
}

@Composable
private fun TopTitle(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.new_sub_title),
        style = titleStyle
    )
}

@Composable
private fun CloseButton(
    modifier: Modifier,
    close: () -> Unit
) {
    IconButton(
        modifier = modifier,
        content = {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
            )
        },
        onClick = close
    )
}