package com.lukakordzaia.feature_add_subscription

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewModelScope
import com.lukakordzaia.core.helpers.DateHelpers
import com.lukakordzaia.core.helpers.SingleEvent
import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.core.utils.LoadingState
import com.lukakordzaia.core.utils.NavConstants
import com.lukakordzaia.core.utils.toJson
import com.lukakordzaia.core.viewmodel.BaseViewModel
import com.lukakordzaia.core_domain.ResultDomain
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.core_domain.usecases.AddSubscriptionUseCase
import com.lukakordzaia.feature_add_subscription.helpers.StringWithError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class AddSubscriptionVM(
    private val addSubscriptionUseCase: AddSubscriptionUseCase
): BaseViewModel<AddSubscriptionState, AddSubscriptionEvent, SingleEvent>() {
    
    override fun createInitialState(): AddSubscriptionState {
        return AddSubscriptionState.initial()
    }

    fun setSubscriptionToEdit(subscription: SubscriptionItemDomain) {
        sendEvent(AddSubscriptionEvent.SetEditableSubscription(subscription))
    }

    fun setLink(link: String) {
        sendEvent(AddSubscriptionEvent.ChangeLink(link))
    }

    fun setName(name: String) {
        sendEvent(AddSubscriptionEvent.ChangeName(StringWithError(name, false)))
    }

    fun setPlan(plan: String) {
        sendEvent(AddSubscriptionEvent.ChangePlan(plan))
    }

    fun setAmount(amount: String) {
        sendEvent(AddSubscriptionEvent.ChangeAmount(StringWithError(amount, false)))
    }

    fun setPeriod(period: Int) {
        sendEvent(AddSubscriptionEvent.ChangePeriod(period))
    }

    fun setSubscriptionType(type: Int) {
        sendEvent(AddSubscriptionEvent.ChangeSubscriptionType(type))
    }

    fun setCurrency(currency: String) {
        sendEvent(AddSubscriptionEvent.ChangeCurrency(currency))
    }

    fun setDate(date: String) {
        sendEvent(AddSubscriptionEvent.ChangeDate(StringWithError(date, false)))
    }

    fun setColor(color: Color) {
        sendEvent(AddSubscriptionEvent.ChangeColor(color))
    }

    fun addSubscription() {
        sendEvent(AddSubscriptionEvent.AddSubscription)
    }

    fun setColorDialogState(state: Boolean) {
        sendEvent(AddSubscriptionEvent.ColorDialogState(state))
    }

    fun setPeriodDialogState(state: Boolean) {
        sendEvent(AddSubscriptionEvent.PeriodDialogState(state))
    }

    fun setSubscriptionTypeDialogState(state: Boolean) {
        sendEvent(AddSubscriptionEvent.SubscriptionTypeDialogState(state))
    }

    fun setCurrencyDialogState(state: Boolean) {
        sendEvent(AddSubscriptionEvent.CurrencyDialogState(state))
    }

    fun setErrorDialogState(state: Boolean) {
        sendEvent(AddSubscriptionEvent.ChangeErrorDialogState(state))
    }

    fun navigateToDetails() {
        val dateFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())

        val subscription = SubscriptionItemDomain(
            id = state.value.editSubscriptionId ?: UUID.randomUUID().toString(),
            name = state.value.nameField.field,
            plan = state.value.planField,
            color = state.value.colorField,
            amount = state.value.amountField.field.toDouble(),
            currency = state.value.currencyField,
            periodType = Constants.PeriodType.getPeriodType(state.value.periodField),
            subscriptionType = Constants.SubscriptionType.getSubscriptionType(state.value.subscriptionTypeField),
            date = if (state.value.dateField.field.isNotEmpty()) dateFormat.parse(state.value.dateField.field)?.time else null,
            updateDate = Calendar.getInstance().time.time
        ).toJson()

        sendEvent(AddSubscriptionEvent.NavigateToDetails(subscription!!))
    }

    private fun addSubscriptionFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            val dateFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())

            if (validateFields()) {
                sendEvent(AddSubscriptionEvent.ChangeLoadingState(LoadingState.LOADING))

                addSubscriptionUseCase.invoke(
                    AddSubscriptionUseCase.Params(
                    auth.currentUser!!.uid,
                    subscription = AddSubscriptionUseCase.Params.AddSubscriptionItem(
                        id = state.value.editSubscriptionId ?: UUID.randomUUID().toString(),
                        name = state.value.nameField.field,
                        plan = state.value.planField,
                        color = state.value.colorField?.toArgb(),
                        amount = state.value.amountField.field.toDouble(),
                        currency = state.value.currencyField,
                        periodType = state.value.periodField,
                        subscriptionType = state.value.subscriptionTypeField,
                        date = if (state.value.dateField.field.isNotEmpty()) dateFormat.parse(state.value.dateField.field)?.time else null,
                        updateDate = Calendar.getInstance().time.time
                    )
                )).collect {
                    when (it) {
                        is ResultDomain.Success -> {
                            sendEvent(AddSubscriptionEvent.UploadDone)
                            sendEvent(AddSubscriptionEvent.ChangeLoadingState(LoadingState.LOADED))
                        }
                        is ResultDomain.Error -> {
                            setErrorDialogState(true)
                            sendEvent(AddSubscriptionEvent.ChangeLoadingState(LoadingState.ERROR))
                        }
                    }
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        return with(state.value) {
            if (nameField.field.isEmpty()) {
                sendEvent(AddSubscriptionEvent.ChangeName(StringWithError("", true)))
            }

            if (amountField.field.isEmpty()) {
                sendEvent(AddSubscriptionEvent.ChangeAmount(StringWithError("", true)))
            }

            if (dateField.field.isEmpty()) {
                sendEvent(AddSubscriptionEvent.ChangeDate(StringWithError("", true)))
            }

            return@with !(nameField.field.isEmpty() || amountField.field.isEmpty() || dateField.field.isEmpty())
        }
    }

    override fun handleEvent(event: AddSubscriptionEvent) {
        when (event) {
            is AddSubscriptionEvent.SetEditableSubscription -> {
                setState {
                    copy(
                        editIsSet = true,
                        editSubscriptionId = event.subscription.id,
                        nameField = StringWithError(event.subscription.name, false),
                        colorField = event.subscription.color,
                        amountField = StringWithError(event.subscription.amount.toString(), false),
                        currencyField = event.subscription.currency,
                        dateField = event.subscription.date?.let {
                            StringWithError(DateHelpers.formatDate(it, "dd/MM/yyyy"), false)
                        } ?: run {
                            StringWithError("", false)
                        },
                        periodField = event.subscription.periodType.type,
                        subscriptionTypeField = event.subscription.subscriptionType.type,
                        planField = event.subscription.plan ?: ""
                    )
                }
            }
            is AddSubscriptionEvent.ChangeLink -> {
                setState { copy(linkField = event.link, keyboardIsVisible = true) }
            }
            is AddSubscriptionEvent.ChangeName -> {
                setState { copy(nameField = event.name, keyboardIsVisible = true) }
            }
            is AddSubscriptionEvent.ColorDialogState -> {
                setState { copy(colorDialogIsOpen = event.state) }
            }
            is AddSubscriptionEvent.ChangeColor -> {
                setState { copy(colorField = event.color, keyboardIsVisible = false) }
            }
            is AddSubscriptionEvent.ChangeAmount -> {
                setState { copy(amountField = event.amount, keyboardIsVisible = true) }
            }
            is AddSubscriptionEvent.CurrencyDialogState -> {
                setState { copy(currencyDialogIsOpen = event.state, keyboardIsVisible = false) }
            }
            is AddSubscriptionEvent.ChangeCurrency -> {
                setState { copy(currencyField = event.currency, keyboardIsVisible = true) }
            }
            is AddSubscriptionEvent.ChangeDate -> {
                setState { copy(dateField = event.date, keyboardIsVisible = true) }
            }
            is AddSubscriptionEvent.PeriodDialogState -> {
                setState { copy(periodDialogIsOpen = event.state) }
            }
            is AddSubscriptionEvent.ChangePeriod -> {
                setState { copy(periodField = event.period, keyboardIsVisible = true) }
            }
            is AddSubscriptionEvent.SubscriptionTypeDialogState -> {
                setState { copy(subscriptionTypeDialogIsOpen = event.state) }
            }
            is AddSubscriptionEvent.ChangeSubscriptionType -> {
                setState { copy(subscriptionTypeField = event.type, keyboardIsVisible = true) }
            }
            is AddSubscriptionEvent.ChangePlan -> {
                setState { copy(planField = event.plan, keyboardIsVisible = true) }
            }
            is AddSubscriptionEvent.ChangeErrorDialogState -> {
                setState { copy(errorDialogIsOpen = event.state, keyboardIsVisible = false) }
            }
            is AddSubscriptionEvent.ChangeLoadingState -> {
                setState { copy(isLoading = event.state, keyboardIsVisible = false) }
            }
            is AddSubscriptionEvent.AddSubscription -> {
                addSubscriptionFirestore()
            }
            is AddSubscriptionEvent.UploadDone -> {
                setState { copy(isUploaded = true) }
            }
            is AddSubscriptionEvent.NavigateToDetails -> {
                setSingleEvent { SingleEvent.Navigation("${NavConstants.SUBSCRIPTION_DETAILS}/${event.subscription}") }
            }
        }
    }
}