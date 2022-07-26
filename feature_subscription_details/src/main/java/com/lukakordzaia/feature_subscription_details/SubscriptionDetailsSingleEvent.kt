package com.lukakordzaia.feature_subscription_details

import com.lukakordzaia.core.helpers.interfaces.UiSingleEvent

sealed class SubscriptionDetailsSingleEvent: UiSingleEvent {
    data class ShowToast(val message: String): SubscriptionDetailsSingleEvent()
    data class Navigation(val destination: String): SubscriptionDetailsSingleEvent()
    data class SubscriptionIsDeleted(val state: Boolean) : SubscriptionDetailsSingleEvent()
}
