package com.lukakordzaia.feature_subscriptions

import com.lukakordzaia.core.helpers.interfaces.UiEvent
import com.lukakordzaia.core.helpers.interfaces.UiState
import com.lukakordzaia.core.utils.LoadingState

sealed class SubscriptionsEvent: UiEvent {
    object GetUserSubscriptions: SubscriptionsEvent()
    object SubscriptionsIsEmpty: SubscriptionsEvent()
    data class SetSubscriptions(val items: List<SubscriptionListType>): SubscriptionsEvent()
    data class SetSubscriptionTotalBalance(val total: Double): SubscriptionsEvent()
    data class ChangeLoadingState(val state: LoadingState): SubscriptionsEvent()
    data class ChangeScrollOffset(val offset: Float): SubscriptionsEvent()
    data class NavigateToDetails(val subscription: String): SubscriptionsEvent()
    object NavigateToStatistics: SubscriptionsEvent()
}

data class SubscriptionsState(
    val isLoading: LoadingState,
    val noSubscriptions: Boolean,
    val subscriptionItems: List<SubscriptionListType>,
    val subscriptionTotalBalance: Double,
    val scrollOffset: Float
): UiState {
    companion object {
        fun initial() = SubscriptionsState(
            isLoading = LoadingState.LOADED,
            noSubscriptions = false,
            subscriptionItems = emptyList(),
            subscriptionTotalBalance = 0.0,
            scrollOffset = 0F
        )
    }
}