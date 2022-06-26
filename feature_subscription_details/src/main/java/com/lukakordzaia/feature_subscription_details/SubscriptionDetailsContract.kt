package com.lukakordzaia.feature_subscription_details

import com.lukakordzaia.core.helpers.UiEvent
import com.lukakordzaia.core.helpers.UiState
import com.lukakordzaia.core.utils.LoadingState
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain

sealed class SubscriptionDetailsEvent: UiEvent {
    data class ChangeLoadingState(val state: LoadingState): SubscriptionDetailsEvent()
    data class GetSubscriptionDetails(val subscription: SubscriptionItemDomain): SubscriptionDetailsEvent()
}

data class SubscriptionDetailsState(
    val isLoading: LoadingState,
    val details: SubscriptionItemDomain?
) : UiState {
    companion object {
        fun initial() = SubscriptionDetailsState(
            isLoading = LoadingState.LOADED,
            details = null
        )
    }
}