package com.lukakordzaia.featurelogin.event

import com.lukakordzaia.core.helpers.UiEvent
import com.lukakordzaia.core.helpers.UiState
import com.lukakordzaia.core.utils.LoadingState

sealed class LoginEvent: UiEvent {
    data class ChangeLoadingState(val state: LoadingState): LoginEvent()
    data class UserLoginToFirebase(val idToken: String): LoginEvent()
    object AddUserToFirestore: LoginEvent()
    data class ChangeLoginState(val isLoggedIn: Boolean): LoginEvent()
    data class ChangeUserAddedState(val isAdded: Boolean): LoginEvent()
}

data class LoginState(
    val isLoading: LoadingState,
    val isLoggedIn: Boolean,
    val isUserAdded: Boolean
): UiState {
    companion object {
        fun initial() = LoginState(
            isLoading = LoadingState.LOADED,
            isLoggedIn = false,
            isUserAdded = false
        )
    }
}