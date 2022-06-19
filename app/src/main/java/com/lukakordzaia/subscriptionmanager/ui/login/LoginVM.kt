package com.lukakordzaia.subscriptionmanager.ui.login

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.GoogleAuthProvider
import com.lukakordzaia.subscriptionmanager.base.BaseViewModel
import com.lukakordzaia.subscriptionmanager.domain.usecases.AddUserFirestoreUseCase
import com.lukakordzaia.subscriptionmanager.domain.usecases.UserLoginUseCase
import com.lukakordzaia.subscriptionmanager.events.LoginEvent
import com.lukakordzaia.subscriptionmanager.events.LoginState
import com.lukakordzaia.subscriptionmanager.helpers.SingleEvent
import com.lukakordzaia.subscriptionmanager.network.LoadingState
import com.lukakordzaia.subscriptionmanager.network.ResultDomain
import com.lukakordzaia.subscriptionmanager.network.networkmodels.UserLoginRequestNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginVM(
    private val userLoginUseCase: UserLoginUseCase,
    private val addUserFirestoreUseCase: AddUserFirestoreUseCase
) : BaseViewModel<LoginState, LoginEvent, SingleEvent>() {

    override fun createInitialState(): LoginState {
        return LoginState.initial()
    }

    init {
        checkUserLogin()
    }

    fun userLogin(idToken: String) {
        sendEvent(LoginEvent.UserLoginToFirebase(idToken))
    }

    fun addUser() {
        sendEvent(LoginEvent.AddUserToFirestore)
    }

    private fun userLoginFirebase(idToken: String) {
        sendEvent(LoginEvent.ChangeLoadingState(LoadingState.LOADING))

        val credential = GoogleAuthProvider.getCredential(idToken, null)

        viewModelScope.launch(Dispatchers.IO) {
            userLoginUseCase.invoke(
                UserLoginRequestNetwork(
                    auth = auth,
                    credential = credential
                )
            ).collect {
                when (it) {
                    is ResultDomain.Success -> {
                        sendEvent(LoginEvent.ChangeLoginState(it.data))
                    }
                    is ResultDomain.Error -> {
                        sendEvent(LoginEvent.ChangeLoadingState(LoadingState.ERROR))
                    }
                }
            }
        }
    }

    private fun addUseFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            addUserFirestoreUseCase.invoke(auth.currentUser).collect {
                when (it) {
                    is ResultDomain.Success -> {
                        sendEvent(LoginEvent.ChangeUserAddedState(it.data))
                        sendEvent(LoginEvent.ChangeLoadingState(LoadingState.LOADED))
                    }
                    is ResultDomain.Error -> {
                        sendEvent(LoginEvent.ChangeLoadingState(LoadingState.ERROR))
                    }
                }
            }
        }
    }

    private fun checkUserLogin() {
        if (auth.currentUser != null) {
            sendEvent(LoginEvent.ChangeLoadingState(LoadingState.LOADING))
            sendEvent(LoginEvent.ChangeLoginState(true))
        }
    }

    override fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.ChangeLoadingState -> {
                setState { copy(isLoading = event.state) }
            }
            is LoginEvent.ChangeLoginState -> {
                setState { copy(isLoggedIn = event.isLoggedIn) }
            }
            is LoginEvent.ChangeUserAddedState -> {
                setState { copy(isUserAdded = event.isAdded) }
            }
            is LoginEvent.UserLoginToFirebase -> {
                userLoginFirebase(event.idToken)
            }
            is LoginEvent.AddUserToFirestore -> {
                addUseFirestore()
            }
        }
    }
}