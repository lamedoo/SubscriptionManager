package com.lukakordzaia.subscriptionmanager.events

import com.lukakordzaia.subscriptionmanager.domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.subscriptionmanager.helpers.UiEvent
import com.lukakordzaia.subscriptionmanager.helpers.UiState
import com.lukakordzaia.subscriptionmanager.network.LoadingState

sealed class SubscriptionDetailsEvent: UiEvent {
    data class ChangeLoadingState(val state: LoadingState): SubscriptionDetailsEvent()
    data class GetSubscriptionDetails(val subscription: SubscriptionItemDomain): SubscriptionDetailsEvent()
}

data class SubscriptionDetailsState(
    val isLoading: LoadingState?,
    val details: SubscriptionItemDomain?
) : UiState {
    companion object {
        fun initial() = SubscriptionDetailsState(
            isLoading = null,
            details = null
        )
    }
}
