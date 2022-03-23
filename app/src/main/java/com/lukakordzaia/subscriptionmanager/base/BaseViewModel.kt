package com.lukakordzaia.subscriptionmanager.base

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.subscriptionmanager.helpers.UiEvent
import com.lukakordzaia.subscriptionmanager.helpers.UiSingleEvent
import com.lukakordzaia.subscriptionmanager.helpers.UiState
import org.koin.core.component.KoinComponent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<STATE: UiState, EVENT: UiEvent, SINGLE_EVENT: UiSingleEvent>: ViewModel(), KoinComponent {
    protected val auth = Firebase.auth

    private val initialState : STATE by lazy { createInitialState() }
    abstract fun createInitialState() : STATE

    abstract fun handleEvent(event: EVENT)

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _singleEvent : Channel<SINGLE_EVENT> = Channel()
    val singleEvent = _singleEvent.receiveAsFlow()

    fun setState(reduce: STATE.() -> STATE) {
        _state.tryEmit(_state.value.reduce())
    }

    fun sendEvent(event: EVENT) {
        handleEvent(event)
    }

    fun setSingleEvent(builder: () -> SINGLE_EVENT) {
        _singleEvent.trySend(builder())
    }
}