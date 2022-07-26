package com.lukakordzaia.core_compose

import androidx.compose.runtime.Composable
import com.lukakordzaia.core.utils.LoadingState
import com.lukakordzaia.core_compose.custom.ProgressDialog

@Composable
fun ObserveLoadingState(
    loader: LoadingState,
    isLoading: @Composable () -> Unit = { ProgressDialog(showDialog = true) },
    isLoaded: @Composable () -> Unit = { ProgressDialog(showDialog = false) },
    isError: @Composable () -> Unit = {}
) {
    when (loader) {
        LoadingState.LOADING -> isLoading.invoke()
        LoadingState.LOADED -> isLoaded.invoke()
        LoadingState.ERROR -> isError.invoke()
    }
}