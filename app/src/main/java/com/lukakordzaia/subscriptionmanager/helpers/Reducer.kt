package com.lukakordzaia.subscriptionmanager.helpers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Reducer<STATE: UiState, EVENT: UiEvent>(initial: STATE) {
    private val _state = MutableStateFlow(initial)
    val state: StateFlow<STATE> = _state.asStateFlow()

    fun sendEvent(event: EVENT) {
        reduce(_state.value, event)
    }

    fun setState(newState: STATE) {
        _state.tryEmit(newState)
    }

    abstract fun reduce(oldState: STATE, event: EVENT)
}

interface UiState
interface UiEvent