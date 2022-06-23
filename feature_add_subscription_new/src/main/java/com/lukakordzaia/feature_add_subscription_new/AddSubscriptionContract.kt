package com.lukakordzaia.feature_add_subscription_new

import androidx.compose.ui.graphics.Color
import com.lukakordzaia.subscriptionmanager.helpers.StringWithError
import com.lukakordzaia.core.helpers.UiEvent
import com.lukakordzaia.core.utils.LoadingState

sealed class AddSubscriptionEvent: UiEvent {
    object EmptyFields: AddSubscriptionEvent()
    data class ChangeLoadingState(val state: LoadingState): AddSubscriptionEvent()
    data class ChangeLink(val link: String): AddSubscriptionEvent()
    data class ChangeName(val name: StringWithError): AddSubscriptionEvent()
    data class ChangePlan(val plan: String): AddSubscriptionEvent()
    data class ChangeAmount(val amount: StringWithError): AddSubscriptionEvent()
    data class PeriodDialogState(val state: Boolean): AddSubscriptionEvent()
    data class ChangePeriod(val period: Int): AddSubscriptionEvent()
    data class CurrencyDialogState(val state: Boolean): AddSubscriptionEvent()
    data class ChangeCurrency(val currency: String): AddSubscriptionEvent()
    data class ChangeDate(val date: String): AddSubscriptionEvent()
    data class ColorDialogState(val state: Boolean): AddSubscriptionEvent()
    data class ChangeColor(val color: Color): AddSubscriptionEvent()
    data class ChangeErrorDialogState(val state: Boolean): AddSubscriptionEvent()
    object AddSubscription: AddSubscriptionEvent()
    object UploadDone: AddSubscriptionEvent()
}

data class AddSubscriptionState(
    val isLoading: LoadingState,
    val linkField: String,
    val nameField: StringWithError,
    val planField: String,
    val amountField: StringWithError,
    val periodField: Int,
    val currencyField: String,
    val dateField: String,
    val colorField: Color?,
    val keyboardIsVisible: Boolean,
    val colorDialogIsOpen: Boolean,
    val periodDialogIsOpen: Boolean,
    val currencyDialogIsOpen: Boolean,
    val errorDialogIsOpen: Boolean,
    val isUploaded: Boolean,
): com.lukakordzaia.core.helpers.UiState {
    companion object {
        fun initial() = AddSubscriptionState(
            isLoading = LoadingState.LOADED,
            linkField = "",
            nameField = StringWithError("", false),
            planField = "",
            amountField = StringWithError("", false),
            periodField = 2,
            currencyField = "USD",
            dateField = "",
            colorField = null,
            keyboardIsVisible = false,
            colorDialogIsOpen = false,
            periodDialogIsOpen = false,
            currencyDialogIsOpen = false,
            errorDialogIsOpen = false,
            isUploaded = false
        )
    }
}