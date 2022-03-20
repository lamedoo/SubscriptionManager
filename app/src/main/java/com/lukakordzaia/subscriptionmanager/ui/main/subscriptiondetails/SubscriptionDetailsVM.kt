package com.lukakordzaia.subscriptionmanager.ui.main.subscriptiondetails

import com.lukakordzaia.subscriptionmanager.base.BaseViewModel
import com.lukakordzaia.subscriptionmanager.domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.subscriptionmanager.events.SubscriptionDetailsEvent
import com.lukakordzaia.subscriptionmanager.events.SubscriptionDetailsState
import com.lukakordzaia.subscriptionmanager.helpers.Reducer
import kotlinx.coroutines.flow.StateFlow

class SubscriptionDetailsVM : BaseViewModel<SubscriptionDetailsState, SubscriptionDetailsEvent>() {
    private val reducer = SubscriptionDetailsReducer(SubscriptionDetailsState.initial())

    override val state: StateFlow<SubscriptionDetailsState>
        get() = reducer.state

    fun getSubscriptionDetails(subscription: SubscriptionItemDomain) {
        reducer.sendEvent(SubscriptionDetailsEvent.GetSubscriptionDetails(subscription))
    }

    inner class SubscriptionDetailsReducer(initial: SubscriptionDetailsState): Reducer<SubscriptionDetailsState, SubscriptionDetailsEvent>(initial) {
        override fun reduce(oldState: SubscriptionDetailsState, event: SubscriptionDetailsEvent) {
            when (event) {
                is SubscriptionDetailsEvent.ChangeLoadingState -> {
                    setState(oldState.copy(isLoading = event.state))
                }
                is SubscriptionDetailsEvent.GetSubscriptionDetails -> {
                    setState(oldState.copy(details = event.subscription))
                }
            }
        }
    }
}