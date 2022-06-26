package com.lukakordzaia.feature_subscription_details

import com.lukakordzaia.core.helpers.SingleEvent
import com.lukakordzaia.core.viewmodel.BaseViewModel

class SubscriptionDetailsVM : BaseViewModel<SubscriptionDetailsState, SubscriptionDetailsEvent, SingleEvent>() {
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