package com.lukakordzaia.subscriptionmanager.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun hideKeyboard(): Boolean {
    LocalSoftwareKeyboardController.current?.hide()
    return true
}