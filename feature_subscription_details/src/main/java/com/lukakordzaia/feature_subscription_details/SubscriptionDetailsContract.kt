package com.lukakordzaia.feature_subscription_details

import com.lukakordzaia.core.helpers.interfaces.UiEvent
import com.lukakordzaia.core.helpers.interfaces.UiState
import com.lukakordzaia.core.utils.LoadingState
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain

sealed class SubscriptionDetailsEvent: UiEvent {
    data class ChangeLoadingState(val state: LoadingState): SubscriptionDetailsEvent()
    data class GetSubscriptionDetails(val subscription: SubscriptionItemDomain): SubscriptionDetailsEvent()
    data class NavigateToEditSubscription(val details: SubscriptionItemDomain): SubscriptionDetailsEvent()
    data class DeleteDialogState(val state: Boolean): SubscriptionDetailsEvent()
    data class DeleteSubscription(val id: String): SubscriptionDetailsEvent()
    data class SubscriptionIsDeleted(val loadingState: LoadingState, val state: Boolean): SubscriptionDetailsEvent()
}

data class SubscriptionDetailsState(
    var isLoading: LoadingState,
    val details: SubscriptionItemDomain?,
    val deleteDialogIsOpen: Boolean,
    val isSubscriptionDeleted: Boolean
) : UiState {
    companion object {
        fun initial() = SubscriptionDetailsState(
            isLoading = LoadingState.LOADED,
            details = null,
            deleteDialogIsOpen = false,
            isSubscriptionDeleted = false
        )
    }
}