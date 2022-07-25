package com.lukakordzaia.feature_subscription_details

import androidx.lifecycle.viewModelScope
import com.lukakordzaia.core.helpers.SingleEvent
import com.lukakordzaia.core.utils.LoadingState
import com.lukakordzaia.core.utils.NavConstants
import com.lukakordzaia.core.viewmodel.BaseViewModel
import com.lukakordzaia.core_domain.ResultDomain
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.core_domain.usecases.DeleteSubscriptionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriptionDetailsVM(
    private val deleteSubscriptionUseCase: DeleteSubscriptionUseCase
) : BaseViewModel<SubscriptionDetailsState, SubscriptionDetailsEvent, SingleEvent>() {
    override fun createInitialState(): SubscriptionDetailsState {
        return SubscriptionDetailsState.initial()
    }

    fun getSubscriptionDetails(subscription: SubscriptionItemDomain) {
        sendEvent(SubscriptionDetailsEvent.GetSubscriptionDetails(subscription))
    }

    fun navigateToEditSubscription(details: SubscriptionItemDomain) {
        sendEvent(SubscriptionDetailsEvent.NavigateToEditSubscription(details))
    }

    fun setDeleteDialogState(state: Boolean) {
        sendEvent(SubscriptionDetailsEvent.DeleteDialogState(state))
    }

    fun deleteSubscription(id: String) {
        sendEvent(SubscriptionDetailsEvent.DeleteSubscription(id))
    }

    private fun deleteSubscriptionFirestore(id: String) {
        sendEvent(SubscriptionDetailsEvent.ChangeLoadingState(LoadingState.LOADING))
        sendEvent(SubscriptionDetailsEvent.DeleteDialogState(false))

        viewModelScope.launch(Dispatchers.IO) {
            deleteSubscriptionUseCase.invoke(
                DeleteSubscriptionUseCase.Params(
                    auth.currentUser!!.uid,
                    id
                )
            ).collect {
                when (it) {
                    is ResultDomain.Success -> {
                        setSingleEvent { SingleEvent.ShowToast("Deleted Successfully") }
                        sendEvent(SubscriptionDetailsEvent.SubscriptionIsDeleted(LoadingState.LOADED,true))
                    }
                    is ResultDomain.Error -> {
                        sendEvent(SubscriptionDetailsEvent.ChangeLoadingState(LoadingState.ERROR))
                    }
                }
            }
        }
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
            is SubscriptionDetailsEvent.DeleteDialogState -> {
                setState { copy(deleteDialogIsOpen = event.state) }
            }
            is SubscriptionDetailsEvent.DeleteSubscription -> {
                deleteSubscriptionFirestore(event.id)
            }
            is SubscriptionDetailsEvent.SubscriptionIsDeleted -> {
                setState { copy(isLoading = event.loadingState, isSubscriptionDeleted = event.state) }
            }
        }
    }
}