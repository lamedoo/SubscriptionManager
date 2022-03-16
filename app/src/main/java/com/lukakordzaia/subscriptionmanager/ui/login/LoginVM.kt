package com.lukakordzaia.subscriptionmanager.ui.login

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.GoogleAuthProvider
import com.lukakordzaia.subscriptionmanager.base.BaseViewModel
import com.lukakordzaia.subscriptionmanager.domain.usecases.AddUserFirestoreUseCase
import com.lukakordzaia.subscriptionmanager.domain.usecases.UserLoginUseCase
import com.lukakordzaia.subscriptionmanager.events.LoginEvent
import com.lukakordzaia.subscriptionmanager.events.LoginState
import com.lukakordzaia.subscriptionmanager.helpers.Reducer
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
) : BaseViewModel<LoginState, LoginEvent>() {
    private val reducer = LoginReducer(LoginState.initial())

    override val state: StateFlow<LoginState>
        get() = reducer.state

    init {
        checkUserLogin()
    }

    fun userLogin(idToken: String) {
        reducer.sendEvent(LoginEvent.UserLoginToFirebase(idToken))
    }

    fun addUser() {
        reducer.sendEvent(LoginEvent.AddUserToFirestore)
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun userLoginFirebase(idToken: String) {
        reducer.sendEvent(LoginEvent.ChangeLoadingState(LoadingState.LOADING))

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
                        reducer.sendEvent(LoginEvent.ChangeLoginState(it.data))
                    }
                    is ResultDomain.Error -> {
                        reducer.sendEvent(LoginEvent.ChangeLoadingState(LoadingState.ERROR))
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
                        reducer.sendEvent(LoginEvent.ChangeUserAddedState(it.data))
                        reducer.sendEvent(LoginEvent.ChangeLoadingState(LoadingState.LOADED))
                    }
                    is ResultDomain.Error -> {
                        reducer.sendEvent(LoginEvent.ChangeLoadingState(LoadingState.ERROR))
                    }
                }
            }
        }
    }

    private fun checkUserLogin() {
        if (auth.currentUser != null) {
            reducer.sendEvent(LoginEvent.ChangeLoadingState(LoadingState.LOADING))
            reducer.sendEvent(LoginEvent.ChangeLoginState(true))
        }
    }

    inner class LoginReducer(initial: LoginState): Reducer<LoginState, LoginEvent>(initial) {
        override fun reduce(oldState: LoginState, event: LoginEvent) {
            when (event) {
                is LoginEvent.ChangeLoadingState -> {
                    setState(oldState.copy(isLoading = event.state))
                }
                is LoginEvent.ChangeLoginState -> {
                    setState(oldState.copy(isLoggedIn = event.isLoggedIn))
                }
                is LoginEvent.ChangeUserAddedState -> {
                    setState(oldState.copy(isUserAdded = event.isAdded))
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
}