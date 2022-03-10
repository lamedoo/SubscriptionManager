package com.lukakordzaia.subscriptionmanager.events

import com.lukakordzaia.subscriptionmanager.helpers.UiEvent
import com.lukakordzaia.subscriptionmanager.helpers.UiState
import com.lukakordzaia.subscriptionmanager.utils.Constants

sealed class AddSubscriptionEvent: UiEvent {
    object EmptyFields: AddSubscriptionEvent()
    data class ChangeLink(val link: String): AddSubscriptionEvent()
    data class ChangeName(val name: String): AddSubscriptionEvent()
    data class ChangePlan(val plan: String): AddSubscriptionEvent()
    data class ChangeAmount(val amount: Double): AddSubscriptionEvent()
    data class ChangePeriod(val period: Constants.PeriodType): AddSubscriptionEvent()
    data class ChangeCurrency(val currency: String): AddSubscriptionEvent()
    data class ChangeDate(val date: String): AddSubscriptionEvent()
    object AddSubscription: AddSubscriptionEvent()
}

data class AddSubscriptionState(
    val isLoading: Boolean,
    val linkField: String,
    val nameField: String,
    val planField: String,
    val amountField: Double,
    val periodField: Constants.PeriodType,
    val currencyField: String,
    val dateField: String,
    val keyboardIsVisible: Boolean
): UiState {
    companion object {
        fun initial() = AddSubscriptionState(
            isLoading = false,
            linkField = "",
            nameField = "",
            planField = "",
            amountField = 0.0,
            periodField = Constants.PeriodType.MONTH,
            currencyField = "USD",
            dateField = "",
            keyboardIsVisible = false
        )
    }
}
