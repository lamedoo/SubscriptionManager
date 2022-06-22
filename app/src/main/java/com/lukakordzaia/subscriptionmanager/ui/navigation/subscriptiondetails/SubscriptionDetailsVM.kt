package com.lukakordzaia.subscriptionmanager.ui.navigation.subscriptiondetails

import com.lukakordzaia.subscriptionmanager.events.SubscriptionDetailsEvent
import com.lukakordzaia.subscriptionmanager.events.SubscriptionDetailsState

class SubscriptionDetailsVM : com.lukakordzaia.core.viewmodel.BaseViewModel<SubscriptionDetailsState, SubscriptionDetailsEvent, com.lukakordzaia.core.helpers.SingleEvent>() {
    override fun createInitialState(): SubscriptionDetailsState {
        return SubscriptionDetailsState.initial()
    }

    fun getSubscriptionDetails(subscription: com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain) {
        sendEvent(SubscriptionDetailsEvent.GetSubscriptionDetails(subscription))
    }

    override fun handleEvent(event: SubscriptionDetailsEvent) {
        when (event) {
            is SubscriptionDetailsEvent.ChangeLoadingState -> {
                setState { copy(isLoading = event.state) }
            }
            is SubscriptionDetailsEvent.GetSubscriptionDetails -> {
                setState { copy(details = event.subscription) }
            }
        }
    }
}