package com.lukakordzaia.core_compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.lukakordzaia.core.helpers.SingleEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ObserveSingleEvents(
    navController: NavHostController,
    singleEvent: Flow<SingleEvent>
) {
    LaunchedEffect(key1 = null) {
        singleEvent.collectLatest {
            when (it) {
                is SingleEvent.Navigation -> {
                    navController.navigate(it.destination)
                }
                is SingleEvent.ShowToast -> {

                }
                is SingleEvent.ShowSnackBar -> {

                }
            }
        }
    }
}