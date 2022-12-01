package com.lukakordzaia.core.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.core.helpers.SingleEvent
import com.lukakordzaia.core.helpers.interfaces.UiEvent
import com.lukakordzaia.core.helpers.interfaces.UiSingleEvent
import com.lukakordzaia.core.helpers.interfaces.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.core.component.KoinComponent

abstract class BaseViewModel<STATE: UiState, EVENT: UiEvent, SINGLE_EVENT: UiSingleEvent>: ViewModel(), KoinComponent {
    protected val auth = Firebase.auth

    private val initialState : STATE by lazy { createInitialState() }
    protected abstract fun createInitialState() : STATE

    protected abstract fun handleEvent(event: EVENT)

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _generalEvent : Channel<SingleEvent> = Channel()
    val generalEvent = _generalEvent.receiveAsFlow()

    protected fun setState(reduce: STATE.() -> STATE) {
        _state.tryEmit(_state.value.reduce())
    }

    fun sendEvent(event: EVENT) {
        handleEvent(event)
    }

    protected fun setSingleEvent(builder: () -> SingleEvent) {
        _generalEvent.trySend(builder())
    }

    fun createToast(message: String) {
        setSingleEvent { SingleEvent.ShowToast(message = message) }
    }
}