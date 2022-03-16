package com.lukakordzaia.subscriptionmanager.ui.main.home

import androidx.lifecycle.viewModelScope
import com.lukakordzaia.subscriptionmanager.base.BaseViewModel
import com.lukakordzaia.subscriptionmanager.domain.usecases.GetSubscriptionsUseCase
import com.lukakordzaia.subscriptionmanager.events.HomeEvent
import com.lukakordzaia.subscriptionmanager.events.HomeState
import com.lukakordzaia.subscriptionmanager.helpers.Reducer
import com.lukakordzaia.subscriptionmanager.network.LoadingState
import com.lukakordzaia.subscriptionmanager.network.ResultDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeVM(
    private val getSubscriptionsUseCase: GetSubscriptionsUseCase
): BaseViewModel<HomeState, HomeEvent>() {
    private val reducer = HomeReducer(HomeState.initial())

    override val state: StateFlow<HomeState>
        get() = reducer.state

    init {
        getUserSubscriptions()
    }

    private fun getUserSubscriptions() {
        reducer.sendEvent(HomeEvent.GetUserSubscriptions)
    }

    private fun userSubscriptionsFirestore() {
        reducer.sendEvent(HomeEvent.ChangeLoadingState(LoadingState.LOADING))

        viewModelScope.launch(Dispatchers.IO) {
            getSubscriptionsUseCase.invoke(auth.currentUser?.uid).collect {
                when (it) {
                    is ResultDomain.Success -> {
                        if (it.data.isNullOrEmpty()) {
                            reducer.sendEvent(HomeEvent.SubscriptionsIsEmpty)
                        } else {
                            reducer.sendEvent(HomeEvent.SetSubscriptions(it.data))
                        }
                        reducer.sendEvent(HomeEvent.ChangeLoadingState(LoadingState.LOADED))
                    }
                    is ResultDomain.Error -> {
                        reducer.sendEvent(HomeEvent.ChangeLoadingState(LoadingState.ERROR))
                    }
                }
            }
        }
    }

    inner class HomeReducer(initial: HomeState): Reducer<HomeState, HomeEvent>(initial) {
        override fun reduce(oldState: HomeState, event: HomeEvent) {
            when (event) {
                is HomeEvent.GetUserSubscriptions -> {
                    userSubscriptionsFirestore()
                }
                is HomeEvent.SubscriptionsIsEmpty -> {
                    setState(oldState.copy(noSubscriptions = true))
                }
                is HomeEvent.SetSubscriptions -> {
                    setState(oldState.copy(subscriptionItems = event.items, noSubscriptions = false))
                }
                is HomeEvent.ChangeLoadingState -> {
                    setState(oldState.copy(isLoading = event.state))
                }
            }
        }
    }
}