package com.lukakordzaia.subscriptionmanager.events

import com.godaddy.android.colorpicker.HsvColor
import com.lukakordzaia.subscriptionmanager.helpers.UiEvent
import com.lukakordzaia.subscriptionmanager.helpers.UiState
import com.lukakordzaia.subscriptionmanager.utils.Constants

sealed class AddSubscriptionEvent: UiEvent {
    object EmptyFields: AddSubscriptionEvent()
    data class ChangeLink(val link: String): AddSubscriptionEvent()
    data class ChangeName(val name: String): AddSubscriptionEvent()
    data class ChangePlan(val plan: String): AddSubscriptionEvent()
    data class ChangeAmount(val amount: String): AddSubscriptionEvent()
    data class PeriodDialogState(val state: Boolean): AddSubscriptionEvent()
    data class ChangePeriod(val period: String): AddSubscriptionEvent()
    data class ChangeCurrency(val currency: String): AddSubscriptionEvent()
    data class ChangeDate(val date: String): AddSubscriptionEvent()
    data class ColorDialogState(val state: Boolean): AddSubscriptionEvent()
    data class ChangeColor(val color: HsvColor): AddSubscriptionEvent()
    object AddSubscription: AddSubscriptionEvent()
}

data class AddSubscriptionState(
    val isLoading: Boolean,
    val linkField: String,
    val nameField: String,
    val planField: String,
    val amountField: String,
    val periodField: String,
    val currencyField: String,
    val dateField: String,
    val colorField: HsvColor?,
    val keyboardIsVisible: Boolean,
    val colorDialogIsOpen: Boolean,
    val periodDialogIsOpen: Boolean
): UiState {
    companion object {
        fun initial() = AddSubscriptionState(
            isLoading = false,
            linkField = "",
            nameField = "",
            planField = "",
            amountField = "",
            periodField = "",
            currencyField = "USD",
            dateField = "",
            colorField = null,
            keyboardIsVisible = false,
            colorDialogIsOpen = false,
            periodDialogIsOpen = false
        )
    }
}
