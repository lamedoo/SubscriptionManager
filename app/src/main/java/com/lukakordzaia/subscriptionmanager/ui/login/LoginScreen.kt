package com.lukakordzaia.subscriptionmanager.ui.login

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat.startActivity
import com.lukakordzaia.subscriptionmanager.R
import com.lukakordzaia.subscriptionmanager.events.LoginState
import com.lukakordzaia.subscriptionmanager.network.LoadingState
import com.lukakordzaia.subscriptionmanager.ui.main.MainActivity
import com.lukakordzaia.subscriptionmanager.ui.theme._1F1F1F
import com.lukakordzaia.subscriptionmanager.ui.theme.smallButtonStyle
import com.lukakordzaia.subscriptionmanager.utils.BoldText
import com.lukakordzaia.subscriptionmanager.utils.LightText
import com.lukakordzaia.subscriptionmanager.utils.ProgressBar

@Composable
fun LoginScreen(
    vm: LoginVM,
    onGButtonClick: () -> Unit,
) {
    val state = vm.state.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (welcomeTitle, googleButton, progressBar) = createRefs()

        StateObserver(
            isLoading = state.value.isLoading,
            isLoggedIn = state.value.isLoggedIn,
            isUserAdded = state.value.isUserAdded,
            onLoginSuccess = { vm.addUser() }
        )

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
            color = MaterialTheme.colors.onSecondary
        )
        BoldText(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.subscription_manager),
            fontSize = 28.sp,
            color = MaterialTheme.colors.onSecondary
        )
        LightText(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.login_to_continue),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSecondary
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
            style = smallButtonStyle,
            color = _1F1F1F
       )
    }
}

@Composable
private fun StateObserver(
    isLoading: LoadingState?,
    isLoggedIn: Boolean,
    isUserAdded: Boolean,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current

    when (isLoading) {
        LoadingState.LOADING -> ProgressBar(showDialog = true)
        LoadingState.LOADED -> ProgressBar(showDialog = false)
        LoadingState.ERROR -> {}
        else -> {}
    }

    if (isLoggedIn) {
        onLoginSuccess.invoke()
    }

    if (isUserAdded) {
        startActivity(context, Intent(context, MainActivity::class.java), null)
    }
}