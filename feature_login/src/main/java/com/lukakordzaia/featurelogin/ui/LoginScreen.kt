package com.lukakordzaia.featurelogin.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lukakordzaia.core.R
import com.lukakordzaia.core.utils.LoadingState
import com.lukakordzaia.core_compose.custom.BoldText
import com.lukakordzaia.core_compose.custom.LightText
import com.lukakordzaia.core_compose.custom.ProgressDialog

@Composable
fun LoginScreen(
    vm: LoginVM,
    onGButtonClick: () -> Unit,
    onUserIsAdded: () -> Unit
) {
    val state = vm.state.collectAsState()

    StateObserver(
        isLoading = state.value.isLoading,
        isLoggedIn = state.value.isLoggedIn,
        isUserAdded = state.value.isUserAdded,
        onLoginSuccess = { vm.addUser() },
        onUserIsAdded = onUserIsAdded
    )

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (welcomeTitle, googleButton) = createRefs()

        WelcomeTitle(
            modifier = Modifier
                .constrainAs(welcomeTitle) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        GoogleButton(
            modifier = Modifier
                .constrainAs(googleButton) {
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onClick = onGButtonClick
        )
    }
}

@Composable
private fun WelcomeTitle(
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        BoldText(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.welcome),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
        BoldText(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.subscription_manager),
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
        LightText(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.login_to_continue),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
private fun GoogleButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .padding(end = 5.dp),
            painter = painterResource(id = R.drawable.icon_google),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.google),
            color = MaterialTheme.colorScheme.onPrimary
       )
    }
}

@Composable
private fun StateObserver(
    isLoading: LoadingState?,
    isLoggedIn: Boolean,
    isUserAdded: Boolean,
    onLoginSuccess: () -> Unit,
    onUserIsAdded: () -> Unit
) {
    when (isLoading) {
        LoadingState.LOADING -> ProgressDialog(showDialog = true)
        LoadingState.LOADED -> ProgressDialog(showDialog = false)
        LoadingState.ERROR -> {}
        else -> {}
    }

    if (isLoggedIn) {
        onLoginSuccess.invoke()
    }

    if (isUserAdded) {
        onUserIsAdded.invoke()
    }
}