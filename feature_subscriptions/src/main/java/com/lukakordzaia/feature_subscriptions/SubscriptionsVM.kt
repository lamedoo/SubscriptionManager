package com.lukakordzaia.feature_subscriptions

import androidx.lifecycle.viewModelScope
import com.lukakordzaia.core.helpers.SingleEvent
import com.lukakordzaia.core.utils.LoadingState
import com.lukakordzaia.core.utils.NavConstants
import com.lukakordzaia.core.viewmodel.BaseViewModel
import com.lukakordzaia.core_domain.ResultDomain
import com.lukakordzaia.core_domain.usecases.GetSubscriptionsUseCase
import com.lukakordzaia.feature_subscriptions.validators.SortSubscriptionsValidator
import com.lukakordzaia.feature_subscriptions.validators.SubscriptionTotalBalanceUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriptionsVM(
    private val getSubscriptionsUseCase: GetSubscriptionsUseCase,
    private val sortSubscriptionsValidator: SortSubscriptionsValidator,
    private val subscriptionTotalBalanceUseCase: SubscriptionTotalBalanceUseCase,
): BaseViewModel<SubscriptionsState, SubscriptionsEvent, SingleEvent>() {

    override fun createInitialState(): SubscriptionsState {
        return SubscriptionsState.initial()
    }

    init {
        sendEvent(SubscriptionsEvent.GetUserSubscriptions)
    }

    fun navigateToDetails(subscription: String) {
        sendEvent(SubscriptionsEvent.NavigateToDetails(subscription))
    }

    fun navigateToStatistics() {
        sendEvent(SubscriptionsEvent.NavigateToStatistics)
    }

    private fun userSubscriptionsFirestore() {
        sendEvent(SubscriptionsEvent.ChangeLoadingState(LoadingState.LOADING))

        viewModelScope.launch(Dispatchers.IO) {
            getSubscriptionsUseCase.invoke(auth.currentUser?.uid).collect {
                when (it) {
                    is ResultDomain.Success -> {
                        if (it.data.isEmpty()) {
                            sendEvent(SubscriptionsEvent.SubscriptionsIsEmpty)
                            sendEvent(SubscriptionsEvent.SetSubscriptionTotalBalance(0.0))
                        } else {
                            sendEvent(SubscriptionsEvent.SetSubscriptions(sortSubscriptionsValidator.invoke(it.data)))
                            sendEvent(SubscriptionsEvent.SetSubscriptionTotalBalance(subscriptionTotalBalanceUseCase.invoke(it.data)))
                        }
                        sendEvent(SubscriptionsEvent.ChangeLoadingState(LoadingState.LOADED))
                    }
                    is ResultDomain.Error -> {
                        sendEvent(SubscriptionsEvent.ChangeLoadingState(LoadingState.ERROR))
                    }
                }
            }
        }
    }

    override fun handleEvent(event: SubscriptionsEvent) {
        when (event) {
            is SubscriptionsEvent.GetUserSubscriptions -> {
                userSubscriptionsFirestore()
            }
            is SubscriptionsEvent.SubscriptionsIsEmpty -> {
                setState { copy(noSubscriptions = true) }
            }
            is SubscriptionsEvent.SetSubscriptions -> {
                setState { copy(subscriptionItems = event.items, noSubscriptions = false) }
            }
            is SubscriptionsEvent.SetSubscriptionTotalBalance -> {
                setState { copy(subscriptionTotalBalance = event.total) }
            }
            is SubscriptionsEvent.ChangeLoadingState -> {
                setState { copy(isLoading = event.state) }
            }
            is SubscriptionsEvent.ChangeScrollOffset -> {
                setState { copy(scrollOffset = event.offset) }
            }
            is SubscriptionsEvent.NavigateToDetails -> {
                setSingleEvent { SingleEvent.Navigation("${NavConstants.SUBSCRIPTION_DETAILS}/${event.subscription}") }
            }
            is SubscriptionsEvent.NavigateToStatistics -> {
                setSingleEvent { SingleEvent.Navigation(NavConstants.STATISTICS) }
            }
        }
    }
}