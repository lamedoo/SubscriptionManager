package com.lukakordzaia.subscriptionmanager.ui.main.addsubscription

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewModelScope
import com.godaddy.android.colorpicker.HsvColor
import com.lukakordzaia.subscriptionmanager.base.BaseViewModel
import com.lukakordzaia.subscriptionmanager.domain.usecases.AddSubscriptionUseCase
import com.lukakordzaia.subscriptionmanager.events.AddSubscriptionEvent
import com.lukakordzaia.subscriptionmanager.events.AddSubscriptionState
import com.lukakordzaia.subscriptionmanager.events.LoginEvent
import com.lukakordzaia.subscriptionmanager.helpers.Reducer
import com.lukakordzaia.subscriptionmanager.helpers.StringWithError
import com.lukakordzaia.subscriptionmanager.network.LoadingState
import com.lukakordzaia.subscriptionmanager.network.ResultDomain
import com.lukakordzaia.subscriptionmanager.network.networkmodels.AddSubscriptionItemNetwork
import com.lukakordzaia.subscriptionmanager.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddSubscriptionVM(
    private val addSubscriptionUseCase: AddSubscriptionUseCase
): BaseViewModel<AddSubscriptionState, AddSubscriptionEvent>() {
    private val reducer = AddSubscriptionReducer(AddSubscriptionState.initial())

    override val state: StateFlow<AddSubscriptionState>
        get() = reducer.state

    fun emptyState() {
        reducer.sendEvent(AddSubscriptionEvent.EmptyFields)
    }

    fun setLink(link: String) {
        reducer.sendEvent(AddSubscriptionEvent.ChangeLink(link))
    }

    fun setName(name: String) {
        reducer.sendEvent(AddSubscriptionEvent.ChangeName(StringWithError(name, false)))
    }

    fun setPlan(plan: String) {
        reducer.sendEvent(AddSubscriptionEvent.ChangePlan(plan))
    }

    fun setAmount(amount: String) {
        reducer.sendEvent(AddSubscriptionEvent.ChangeAmount(StringWithError(amount, false)))
    }

    fun setPeriod(period: Int) {
        reducer.sendEvent(AddSubscriptionEvent.ChangePeriod(period))
    }

    fun setCurrency(currency: String) {
        reducer.sendEvent(AddSubscriptionEvent.ChangeCurrency(currency))
    }

    fun setDate(date: String) {
        reducer.sendEvent(AddSubscriptionEvent.ChangeDate(date))
    }

    fun setColor(color: Color) {
        reducer.sendEvent(AddSubscriptionEvent.ChangeColor(color))
    }

    fun addSubscription() {
        reducer.sendEvent(AddSubscriptionEvent.AddSubscription)
    }

    fun setColorDialogState(state: Boolean) {
        reducer.sendEvent(AddSubscriptionEvent.ColorDialogState(state))
    }

    fun setPeriodDialogState(state: Boolean) {
        reducer.sendEvent(AddSubscriptionEvent.PeriodDialogState(state))
    }

    fun setCurrencyDialogState(state: Boolean) {
        reducer.sendEvent(AddSubscriptionEvent.CurrencyDialogState(state))
    }

    fun setErrorDialogState(state: Boolean) {
        reducer.sendEvent(AddSubscriptionEvent.ChangeErrorDialogState(state))
    }

    private fun addSubscriptionFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            val dateFormat = SimpleDateFormat("d/m/yyyy", Locale.getDefault())

            if (validateFields()) {
                reducer.sendEvent(AddSubscriptionEvent.ChangeLoadingState(LoadingState.LOADING))

                addSubscriptionUseCase.invoke(AddSubscriptionUseCase.Params(
                    auth.currentUser!!.uid,
                    subscription = AddSubscriptionItemNetwork(
                        id = UUID.randomUUID().toString(),
                        name = state.value.nameField.field,
                        plan = state.value.planField,
                        color = state.value.colorField?.toArgb(),
                        amount = state.value.amountField.field.toDouble(),
                        currency = state.value.currencyField,
                        periodType = state.value.periodField,
                        date = if (state.value.dateField.isNotEmpty()) dateFormat.parse(state.value.dateField).time else null,
                        updateDate = Calendar.getInstance().time.time
                    )
                )).collect {
                    when (it) {
                        is ResultDomain.Success -> {
                            reducer.sendEvent(AddSubscriptionEvent.UploadDone)
                            reducer.sendEvent(AddSubscriptionEvent.ChangeLoadingState(LoadingState.LOADED))
                        }
                        is ResultDomain.Error -> {
                            setErrorDialogState(true)
                            reducer.sendEvent(AddSubscriptionEvent.ChangeLoadingState(LoadingState.ERROR))
                        }
                    }
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        return with(state.value) {
            if (nameField.field.isEmpty()) {
                reducer.sendEvent(AddSubscriptionEvent.ChangeName(StringWithError("", true)))
            }

            if (amountField.field.isEmpty()) {
                reducer.sendEvent(AddSubscriptionEvent.ChangeAmount(StringWithError("", true)))
            }

            return@with !(nameField.field.isEmpty() || amountField.field.isEmpty())
        }
    }

    inner class AddSubscriptionReducer(initial: AddSubscriptionState): Reducer<AddSubscriptionState, AddSubscriptionEvent>(initial) {
        override fun reduce(oldState: AddSubscriptionState, event: AddSubscriptionEvent) {
            when (event) {
                is AddSubscriptionEvent.EmptyFields -> {
                    setState(AddSubscriptionState.initial())
                }
                is AddSubscriptionEvent.ChangeLink -> {
                    setState(oldState.copy(linkField = event.link, keyboardIsVisible = true))
                }
                is AddSubscriptionEvent.ChangeName -> {
                    setState(oldState.copy(nameField = event.name, keyboardIsVisible = true))
                }
                is AddSubscriptionEvent.ColorDialogState -> {
                    setState(oldState.copy(colorDialogIsOpen = event.state))
                }
                is AddSubscriptionEvent.ChangeColor -> {
                    setState(oldState.copy(colorField = event.color, keyboardIsVisible = false))
                }
                is AddSubscriptionEvent.ChangeAmount -> {
                    setState(oldState.copy(amountField = event.amount, keyboardIsVisible = true))
                }
                is AddSubscriptionEvent.CurrencyDialogState -> {
                    setState(oldState.copy(currencyDialogIsOpen = event.state, keyboardIsVisible = false))
                }
                is AddSubscriptionEvent.ChangeCurrency -> {
                    setState(oldState.copy(currencyField = event.currency, keyboardIsVisible = true))
                }
                is AddSubscriptionEvent.ChangeDate -> {
                    setState(oldState.copy(dateField = event.date, keyboardIsVisible = true))
                }
                is AddSubscriptionEvent.PeriodDialogState -> {
                    setState(oldState.copy(periodDialogIsOpen = event.state))
                }
                is AddSubscriptionEvent.ChangePeriod -> {
                    setState(oldState.copy(periodField = event.period, keyboardIsVisible = true))
                }
                is AddSubscriptionEvent.ChangePlan -> {
                    setState(oldState.copy(planField = event.plan, keyboardIsVisible = true))
                }
                is AddSubscriptionEvent.ChangeErrorDialogState -> {
                    setState(oldState.copy(errorDialogIsOpen = event.state, keyboardIsVisible = false))
                }
                is AddSubscriptionEvent.ChangeLoadingState -> {
                    setState(oldState.copy(isLoading = event.state, keyboardIsVisible = false))
                }
                is AddSubscriptionEvent.AddSubscription -> {
                    addSubscriptionFirestore()
                }
                is AddSubscriptionEvent.UploadDone -> {
                    setState(oldState.copy(isUploaded = true))
                }
            }
        }
    }
}