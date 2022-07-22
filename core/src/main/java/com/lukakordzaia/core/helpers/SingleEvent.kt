package com.lukakordzaia.core.helpers

import com.lukakordzaia.core.helpers.interfaces.UiSingleEvent

sealed class SingleEvent: UiSingleEvent {
    data class ShowToast(val message: String): SingleEvent()
    data class ShowSnackBar(val message: String): SingleEvent()
    data class Navigation(val destination: String): SingleEvent()
}
