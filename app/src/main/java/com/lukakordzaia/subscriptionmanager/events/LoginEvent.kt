package com.lukakordzaia.subscriptionmanager.events

import com.lukakordzaia.subscriptionmanager.helpers.UiEvent
import com.lukakordzaia.subscriptionmanager.helpers.UiState
import com.lukakordzaia.subscriptionmanager.network.LoadingState

sealed class LoginEvent: UiEvent {
    data class ChangeLoading(val state: LoadingState): LoginEvent()
    data class FirebaseLogin(val isLoggedIn: Boolean): LoginEvent()
    data class AddUserFirestore(val isAdded: Boolean): LoginEvent()
}

data class LoginState(
    val isLoading: LoadingState?,
    val isLoggedIn: Boolean,
    val isUserAdded: Boolean
): UiState {
    companion object {
        fun initial() = LoginState(
            isLoading = null,
            isLoggedIn = false,
            isUserAdded = false
        )
    }
}