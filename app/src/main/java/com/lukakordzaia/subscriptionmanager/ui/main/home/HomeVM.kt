package com.lukakordzaia.subscriptionmanager.ui.main.home

import androidx.lifecycle.viewModelScope
import com.lukakordzaia.subscriptionmanager.base.BaseViewModel
import com.lukakordzaia.subscriptionmanager.domain.usecases.GetSubscriptionsUseCase
import com.lukakordzaia.subscriptionmanager.events.HomeEvent
import com.lukakordzaia.subscriptionmanager.events.HomeState
import com.lukakordzaia.subscriptionmanager.helpers.Navigation
import com.lukakordzaia.subscriptionmanager.helpers.SingleEvent
import com.lukakordzaia.subscriptionmanager.network.LoadingState
import com.lukakordzaia.subscriptionmanager.network.ResultDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.min

class HomeVM(
    private val getSubscriptionsUseCase: GetSubscriptionsUseCase
): BaseViewModel<HomeState, HomeEvent, SingleEvent>() {

    override fun createInitialState(): HomeState {
        return HomeState.initial()
    }

    init {
        getUserSubscriptions()
    }

    private fun getUserSubscriptions() {
        sendEvent(HomeEvent.GetUserSubscriptions)
    }

    fun navigateToDetails() {
        sendEvent(HomeEvent.NavigateToDetails)
    }

    private fun userSubscriptionsFirestore() {
        sendEvent(HomeEvent.ChangeLoadingState(LoadingState.LOADING))

        viewModelScope.launch(Dispatchers.IO) {
            getSubscriptionsUseCase.invoke(auth.currentUser?.uid).collect {
                when (it) {
                    is ResultDomain.Success -> {
                        if (it.data.isNullOrEmpty()) {
                            sendEvent(HomeEvent.SubscriptionsIsEmpty)
                        } else {
                            sendEvent(HomeEvent.SetSubscriptions(it.data))
                        }
                        sendEvent(HomeEvent.ChangeLoadingState(LoadingState.LOADED))
                    }
                    is ResultDomain.Error -> {
                        sendEvent(HomeEvent.ChangeLoadingState(LoadingState.ERROR))
                    }
                }
            }
        }
    }

    fun setScrollOffset(visibleItem: Int) {
        sendEvent(HomeEvent.ChangeScrollOffset(
            min(
                1f,
                1 - (((visibleItem * 10) / 100F) + ((visibleItem * 100) / 1000F) + ((visibleItem * 1000) / 10000F))
            )
        ))
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetUserSubscriptions -> {
                userSubscriptionsFirestore()
            }
            is HomeEvent.SubscriptionsIsEmpty -> {
                setState { copy(noSubscriptions = true) }
            }
            is HomeEvent.SetSubscriptions -> {
                setState { copy(subscriptionItems = event.items, noSubscriptions = false) }
            }
            is HomeEvent.ChangeLoadingState -> {
                setState { copy(isLoading = event.state) }
            }
            is HomeEvent.ChangeScrollOffset -> {
                setState { copy(scrollOffset = event.offset) }
            }
            is HomeEvent.NavigateToDetails -> {
                setSingleEvent { SingleEvent.Navigation(Navigation.SUBSCRIPTION_DETAILS) }
            }
        }
    }
}