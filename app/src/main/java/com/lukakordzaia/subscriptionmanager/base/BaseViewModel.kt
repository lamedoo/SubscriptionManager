package com.lukakordzaia.subscriptionmanager.base

import androidx.lifecycle.ViewModel
import com.lukakordzaia.subscriptionmanager.helpers.UiEvent
import com.lukakordzaia.subscriptionmanager.helpers.UiState
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<STATE: UiState, Event: UiEvent>: ViewModel() {
    abstract val state: Flow<STATE>
}