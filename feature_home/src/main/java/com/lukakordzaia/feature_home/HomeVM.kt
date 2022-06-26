package com.lukakordzaia.feature_home

import androidx.lifecycle.viewModelScope
import com.lukakordzaia.core.helpers.SingleEvent
import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.core.utils.LoadingState
import com.lukakordzaia.core.utils.NavConstants
import com.lukakordzaia.core.viewmodel.BaseViewModel
import com.lukakordzaia.core_domain.ResultDomain
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.core_domain.usecases.GetSubscriptionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.min

class HomeVM(
    private val getSubscriptionsUseCase: GetSubscriptionsUseCase
): BaseViewModel<HomeState, HomeEvent, SingleEvent>() {

    override fun createInitialState(): HomeState {
        return HomeState.initial()
    }

    init {
        sendEvent(HomeEvent.GetUserSubscriptions)
    }

    fun navigateToDetails(subscription: String) {
        sendEvent(HomeEvent.NavigateToDetails(subscription))
    }

    fun navigateToStatistics() {
        sendEvent(HomeEvent.NavigateToStatistics)
    }

    private fun userSubscriptionsFirestore() {
        sendEvent(HomeEvent.ChangeLoadingState(LoadingState.LOADING))

        viewModelScope.launch(Dispatchers.IO) {
            getSubscriptionsUseCase.invoke(auth.currentUser?.uid).collect {
                when (it) {
                    is ResultDomain.Success -> {
                        if (it.data.isEmpty()) {
                            sendEvent(HomeEvent.SubscriptionsIsEmpty)
                        } else {
                            sendEvent(HomeEvent.SetSubscriptions(sortSubscriptions(it.data)))
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

    private fun sortSubscriptions(data: List<SubscriptionItemDomain>): List<HomeSubscriptionType> {
        val music = data.filter { it.subscriptionType == Constants.SubscriptionType.MUSIC }
        val entertainment = data.filter { it.subscriptionType == Constants.SubscriptionType.ENTERTAINMENT }
        val online = data.filter { it.subscriptionType == Constants.SubscriptionType.ONLINE }
        val videoStreaming = data.filter { it.subscriptionType == Constants.SubscriptionType.VIDEO_STREAMING }
        val profession = data.filter { it.subscriptionType == Constants.SubscriptionType.PROFESSION }
        val other = data.filter { it.subscriptionType == Constants.SubscriptionType.OTHER }

        val list: MutableList<HomeSubscriptionType> = ArrayList()

        if (music.isNotEmpty()) {
            list.add(0, HomeSubscriptionType.Header(com.lukakordzaia.core.R.string.music))
            list.addAll(list.size, music.map { HomeSubscriptionType.Item(it) })
        }

        if (entertainment.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, HomeSubscriptionType.Header(com.lukakordzaia.core.R.string.entertainment))
            list.addAll(list.size, entertainment.map { HomeSubscriptionType.Item(it) })
        }

        if (online.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, HomeSubscriptionType.Header(com.lukakordzaia.core.R.string.online))
            list.addAll(list.size, online.map { HomeSubscriptionType.Item(it) })
        }

        if (videoStreaming.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, HomeSubscriptionType.Header(com.lukakordzaia.core.R.string.video_streaming))
            list.addAll(list.size, videoStreaming.map { HomeSubscriptionType.Item(it) })
        }

        if (profession.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, HomeSubscriptionType.Header(com.lukakordzaia.core.R.string.profession))
            list.addAll(list.size, profession.map { HomeSubscriptionType.Item(it) })
        }

        if (other.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, HomeSubscriptionType.Header(com.lukakordzaia.core.R.string.other))
            list.addAll(list.size, other.map { HomeSubscriptionType.Item(it) })
        }

        return list
    }

    fun setScrollOffset(visibleItem: Int) {
        sendEvent(
            HomeEvent.ChangeScrollOffset(
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
                setSingleEvent { SingleEvent.Navigation("${NavConstants.SUBSCRIPTION_DETAILS}/${event.subscription}") }
            }
            is HomeEvent.NavigateToStatistics -> {
                setSingleEvent { SingleEvent.Navigation(NavConstants.STATISTICS) }
            }
        }
    }
}