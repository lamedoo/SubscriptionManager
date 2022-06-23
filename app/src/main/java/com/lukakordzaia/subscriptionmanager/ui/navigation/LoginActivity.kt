package com.lukakordzaia.subscriptionmanager.ui.navigation

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.lukakordzaia.core.activity.BaseComponentActivity
import com.lukakordzaia.core_compose.theme.LoginTheme
import com.lukakordzaia.featurelogin.ui.LoginScreen
import com.lukakordzaia.featurelogin.ui.LoginVM
import com.lukakordzaia.subscriptionmanager.R
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseComponentActivity() {
    private val viewModel = viewModel<LoginVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            LoginContent(
                onGButtonClick = {
                    val signInIntent = googleSignInClient.signInIntent
                    startActivityForResult(signInIntent, RC_SIGN_IN)
                }
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                viewModel.value.userLogin(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    companion object {
        const val RC_SIGN_IN = 13
    }
}

@Composable
private fun LoginContent(
    onGButtonClick: () -> Unit
) {
    val context = LocalContext.current

    LoginTheme {
        Surface(
            color = MaterialTheme.colors.secondary
        ) {
            LoginScreen(
                getViewModel(),
                onGButtonClick = onGButtonClick,
                onUserIsAdded = {
                    startActivity(context, Intent(context, MainActivity::class.java), null)
                }
            )
        }
    }
}