package com.lukakordzaia.subscriptionmanager.events

import androidx.compose.ui.graphics.Color
import com.godaddy.android.colorpicker.HsvColor
import com.lukakordzaia.subscriptionmanager.helpers.UiEvent
import com.lukakordzaia.subscriptionmanager.helpers.UiState
import com.lukakordzaia.subscriptionmanager.network.LoadingState
import com.lukakordzaia.subscriptionmanager.utils.Constants

sealed class AddSubscriptionEvent: UiEvent {
    object EmptyFields: AddSubscriptionEvent()
    data class ChangeLoadingState(val state: LoadingState): AddSubscriptionEvent()
    data class ChangeLink(val link: String): AddSubscriptionEvent()
    data class ChangeName(val name: String): AddSubscriptionEvent()
    data class ChangePlan(val plan: String): AddSubscriptionEvent()
    data class ChangeAmount(val amount: String): AddSubscriptionEvent()
    data class PeriodDialogState(val state: Boolean): AddSubscriptionEvent()
    data class ChangePeriod(val period: Int): AddSubscriptionEvent()
    data class ChangeCurrency(val currency: String): AddSubscriptionEvent()
    data class ChangeDate(val date: String): AddSubscriptionEvent()
    data class ColorDialogState(val state: Boolean): AddSubscriptionEvent()
    data class ChangeColor(val color: Color): AddSubscriptionEvent()
    data class ChangeErrorDialogState(val state: Boolean): AddSubscriptionEvent()
    object AddSubscription: AddSubscriptionEvent()
}

data class AddSubscriptionState(
    val isLoading: LoadingState?,
    val linkField: String,
    val nameField: String,
    val planField: String,
    val amountField: String,
    val periodField: Int,
    val currencyField: String,
    val dateField: String,
    val colorField: Color?,
    val keyboardIsVisible: Boolean,
    val colorDialogIsOpen: Boolean,
    val periodDialogIsOpen: Boolean,
    val errorDialogIsOpen: Boolean,
    val isUploaded: Boolean,
): UiState {
    companion object {
        fun initial() = AddSubscriptionState(
            isLoading = null,
            linkField = "",
            nameField = "",
            planField = "",
            amountField = "",
            periodField = 3,
            currencyField = "USD",
            dateField = "",
            colorField = null,
            keyboardIsVisible = false,
            colorDialogIsOpen = false,
            periodDialogIsOpen = false,
            errorDialogIsOpen = false,
            isUploaded = false
        )
    }
}
