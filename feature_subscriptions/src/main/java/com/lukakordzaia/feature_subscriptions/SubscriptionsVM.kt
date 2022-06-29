package com.lukakordzaia.feature_subscriptions

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

class SubscriptionsVM(
    private val getSubscriptionsUseCase: GetSubscriptionsUseCase
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
                        } else {
                            sendEvent(SubscriptionsEvent.SetSubscriptions(sortSubscriptions(it.data)))
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

    private fun sortSubscriptions(data: List<SubscriptionItemDomain>): List<SubscriptionListType> {
        val music = data.filter { it.subscriptionType == Constants.SubscriptionType.MUSIC }
        val entertainment = data.filter { it.subscriptionType == Constants.SubscriptionType.ENTERTAINMENT }
        val online = data.filter { it.subscriptionType == Constants.SubscriptionType.ONLINE }
        val videoStreaming = data.filter { it.subscriptionType == Constants.SubscriptionType.VIDEO_STREAMING }
        val profession = data.filter { it.subscriptionType == Constants.SubscriptionType.PROFESSION }
        val other = data.filter { it.subscriptionType == Constants.SubscriptionType.OTHER }

        val list: MutableList<SubscriptionListType> = ArrayList()

        if (music.isNotEmpty()) {
            list.add(0, SubscriptionListType.Header(com.lukakordzaia.core.R.string.music))
            list.addAll(list.size, music.map { SubscriptionListType.Item(it) })
        }

        if (entertainment.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, SubscriptionListType.Header(com.lukakordzaia.core.R.string.entertainment))
            list.addAll(list.size, entertainment.map { SubscriptionListType.Item(it) })
        }

        if (online.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, SubscriptionListType.Header(com.lukakordzaia.core.R.string.online))
            list.addAll(list.size, online.map { SubscriptionListType.Item(it) })
        }

        if (videoStreaming.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, SubscriptionListType.Header(com.lukakordzaia.core.R.string.video_streaming))
            list.addAll(list.size, videoStreaming.map { SubscriptionListType.Item(it) })
        }

        if (profession.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, SubscriptionListType.Header(com.lukakordzaia.core.R.string.profession))
            list.addAll(list.size, profession.map { SubscriptionListType.Item(it) })
        }

        if (other.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, SubscriptionListType.Header(com.lukakordzaia.core.R.string.other))
            list.addAll(list.size, other.map { SubscriptionListType.Item(it) })
        }

        return list
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