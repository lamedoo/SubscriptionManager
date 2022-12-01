package com.lukakordzaia.feature_add_subscription

import androidx.compose.ui.graphics.Color
import com.lukakordzaia.core.helpers.interfaces.UiEvent
import com.lukakordzaia.core.helpers.interfaces.UiState
import com.lukakordzaia.core.utils.LoadingState
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.feature_add_subscription.helpers.StringWithError

sealed class AddSubscriptionEvent: UiEvent {
    data class ChangeLoadingState(val state: LoadingState): AddSubscriptionEvent()
    data class SetEditableSubscription(val subscription: SubscriptionItemDomain): AddSubscriptionEvent()
    data class ChangeLink(val link: String): AddSubscriptionEvent()
    data class ChangeName(val name: StringWithError): AddSubscriptionEvent()
    data class ChangePlan(val plan: String): AddSubscriptionEvent()
    data class ChangeAmount(val amount: StringWithError): AddSubscriptionEvent()
    data class PeriodDialogState(val state: Boolean): AddSubscriptionEvent()
    data class ChangePeriod(val period: Int): AddSubscriptionEvent()
    data class SubscriptionTypeDialogState(val state: Boolean): AddSubscriptionEvent()
    data class ChangeSubscriptionType(val type: Int): AddSubscriptionEvent()
    data class CurrencyDialogState(val state: Boolean): AddSubscriptionEvent()
    data class ChangeCurrency(val currency: String): AddSubscriptionEvent()
    data class ChangeDate(val date: StringWithError): AddSubscriptionEvent()
    data class ColorDialogState(val state: Boolean): AddSubscriptionEvent()
    data class ChangeColor(val color: Color): AddSubscriptionEvent()
    data class ChangeErrorDialogState(val state: Boolean): AddSubscriptionEvent()
    object AddSubscription: AddSubscriptionEvent()
    object UploadDone: AddSubscriptionEvent()
    data class NavigateToDetails(val subscription: String): AddSubscriptionEvent()
}

data class AddSubscriptionState(
    val editIsSet: Boolean,
    val editSubscriptionId: String?,
    val isLoading: LoadingState,
    val linkField: String,
    val nameField: StringWithError,
    val planField: String,
    val amountField: StringWithError,
    val periodField: Int,
    val subscriptionTypeField: Int,
    val currencyField: String,
    val dateField: StringWithError,
    val colorField: Color?,
    val keyboardIsVisible: Boolean,
    val colorDialogIsOpen: Boolean,
    val periodDialogIsOpen: Boolean,
    val subscriptionTypeDialogIsOpen: Boolean,
    val currencyDialogIsOpen: Boolean,
    val errorDialogIsOpen: Boolean,
    val isUploaded: Boolean
): UiState {
    companion object {
        fun initial() = AddSubscriptionState(
            editIsSet = false,
            editSubscriptionId = null,
            isLoading = LoadingState.LOADED,
            linkField = "",
            nameField = StringWithError("", false),
            planField = "",
            amountField = StringWithError("", false),
            periodField = 2,
            subscriptionTypeField = 5,
            currencyField = "USD",
            dateField = StringWithError("", false),
            colorField = null,
            keyboardIsVisible = false,
            colorDialogIsOpen = false,
            periodDialogIsOpen = false,
            subscriptionTypeDialogIsOpen = false,
            currencyDialogIsOpen = false,
            errorDialogIsOpen = false,
            isUploaded = false
        )
    }
}