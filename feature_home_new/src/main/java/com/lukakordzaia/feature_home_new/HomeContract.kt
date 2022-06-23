package com.lukakordzaia.feature_home_new

import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.core.helpers.UiEvent
import com.lukakordzaia.core.helpers.UiState
import com.lukakordzaia.core.utils.LoadingState

sealed class HomeEvent: UiEvent {
    object GetUserSubscriptions: HomeEvent()
    object SubscriptionsIsEmpty: HomeEvent()
    data class SetSubscriptions(val items: List<SubscriptionItemDomain>): HomeEvent()
    data class ChangeLoadingState(val state: LoadingState): HomeEvent()
    data class ChangeScrollOffset(val offset: Float): HomeEvent()
    data class NavigateToDetails(val subscription: String): HomeEvent()
}

data class HomeState(
    val isLoading: LoadingState,
    val noSubscriptions: Boolean,
    val subscriptionItems: List<SubscriptionItemDomain>,
    val scrollOffset: Float
): UiState {
    companion object {
        fun initial() = HomeState(
            isLoading = LoadingState.LOADED,
            noSubscriptions = false,
            subscriptionItems = emptyList(),
            scrollOffset = 0F
        )
    }
}