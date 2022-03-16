package com.lukakordzaia.subscriptionmanager.base

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.subscriptionmanager.helpers.UiEvent
import com.lukakordzaia.subscriptionmanager.helpers.UiState
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

abstract class BaseViewModel<STATE: UiState, EVENT: UiEvent>: ViewModel(), KoinComponent {
    protected val auth = Firebase.auth

    abstract val state: Flow<STATE>
}