package com.lukakordzaia.subscriptionmanager.ui.addsubscription

import com.lukakordzaia.subscriptionmanager.base.BaseViewModel
import com.lukakordzaia.subscriptionmanager.events.AddSubscriptionEvent
import com.lukakordzaia.subscriptionmanager.events.AddSubscriptionState
import com.lukakordzaia.subscriptionmanager.helpers.Reducer
import com.lukakordzaia.subscriptionmanager.utils.Constants
import kotlinx.coroutines.flow.StateFlow

class AddSubscriptionVM: BaseViewModel<AddSubscriptionState, AddSubscriptionEvent>() {
    private var reducer = AddSubscriptionReducer(AddSubscriptionState.initial())

    override val state: StateFlow<AddSubscriptionState>
        get() = reducer.state

    fun emptyState() {
        reducer.sendEvent(AddSubscriptionEvent.EmptyFields)
    }

    fun setLink(link: String) {
        reducer.sendEvent(AddSubscriptionEvent.ChangeLink(link))
    }

    fun setName(name: String) {
        reducer.sendEvent(AddSubscriptionEvent.ChangeName(name))
    }

    fun setPlan(plan: String) {
        reducer.sendEvent(AddSubscriptionEvent.ChangePlan(plan))
    }

    fun setAmount(amount: String) {
        reducer.sendEvent(AddSubscriptionEvent.ChangeAmount(amount.toDouble()))
    }

    fun setPeriod(period: String) {
        reducer.sendEvent(AddSubscriptionEvent.ChangePeriod(period as Constants.PeriodType))
    }

    fun setCurrency(currency: String) {
        reducer.sendEvent(AddSubscriptionEvent.ChangeCurrency(currency))
    }

    fun setDate(date: String) {
        reducer.sendEvent(AddSubscriptionEvent.ChangeDate(date))
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
                is AddSubscriptionEvent.ChangeAmount -> {
                    setState(oldState.copy(amountField = event.amount, keyboardIsVisible = true))
                }
                is AddSubscriptionEvent.ChangeCurrency -> {
                    setState(oldState.copy(currencyField = event.currency, keyboardIsVisible = true))
                }
                is AddSubscriptionEvent.ChangeDate -> {
                    setState(oldState.copy(dateField = event.date, keyboardIsVisible = true))
                }
                is AddSubscriptionEvent.ChangePeriod -> {
                    setState(oldState.copy(periodField = event.period, keyboardIsVisible = true))
                }
                is AddSubscriptionEvent.ChangePlan -> {
                    setState(oldState.copy(planField = event.plan, keyboardIsVisible = true))
                }
                is AddSubscriptionEvent.AddSubscription -> {

                }
            }
        }
    }
}