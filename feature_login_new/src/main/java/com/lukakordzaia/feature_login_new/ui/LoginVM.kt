package com.lukakordzaia.featurelogin.ui

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.GoogleAuthProvider
import com.lukakordzaia.core.helpers.SingleEvent
import com.lukakordzaia.core.utils.LoadingState
import com.lukakordzaia.core.viewmodel.BaseViewModel
import com.lukakordzaia.core_domain.ResultDomain
import com.lukakordzaia.core_domain.usecases.AddUserFirestoreUseCase
import com.lukakordzaia.core_domain.usecases.UserLoginUseCase
import com.lukakordzaia.featurelogin.event.LoginEvent
import com.lukakordzaia.featurelogin.event.LoginState
import kotlinx.coroutines.Dispatchers
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
                UserLoginUseCase.Params(auth = auth, credential = credential)
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