package com.lukakordzaia.subscriptionmanager.helpers

sealed class SingleEvent: UiSingleEvent {
    data class ShowToast(val message: String): SingleEvent()
    data class ShowSnackBar(val message: String): SingleEvent()
    data class Navigation(val destination: String): SingleEvent()
}
