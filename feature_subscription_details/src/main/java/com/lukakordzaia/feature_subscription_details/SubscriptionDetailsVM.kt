package com.lukakordzaia.feature_subscription_details

import com.lukakordzaia.core.helpers.SingleEvent
import com.lukakordzaia.core.utils.NavConstants
import com.lukakordzaia.core.viewmodel.BaseViewModel
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain

class SubscriptionDetailsVM : BaseViewModel<SubscriptionDetailsState, SubscriptionDetailsEvent, SingleEvent>() {
    override fun createInitialState(): SubscriptionDetailsState {
        return SubscriptionDetailsState.initial()
    }

    fun getSubscriptionDetails(subscription: SubscriptionItemDomain) {
        sendEvent(SubscriptionDetailsEvent.GetSubscriptionDetails(subscription))
    }

    fun navigateToEditSubscription(details: SubscriptionItemDomain) {
        sendEvent(SubscriptionDetailsEvent.NavigateToEditSubscription(details))
    }

    fun deleteSubscription() {

    }

    override fun handleEvent(event: SubscriptionDetailsEvent) {
        when (event) {
            is SubscriptionDetailsEvent.ChangeLoadingState -> {
                setState { copy(isLoading = event.state) }
            }
            is SubscriptionDetailsEvent.GetSubscriptionDetails -> {
                setState { copy(details = event.subscription) }
            }
            is SubscriptionDetailsEvent.NavigateToEditSubscription -> {
                setSingleEvent { SingleEvent.Navigation(NavConstants.EDIT_SUBSCRIPTION) }
            }
        }
    }
}