package com.lukakordzaia.core_compose

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.lukakordzaia.core.helpers.SingleEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ObserveSingleEvents(
    navController: NavHostController,
    singleEvent: Flow<SingleEvent>
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = null) {
        singleEvent.collectLatest {
            when (it) {
                is SingleEvent.Navigation -> {
                    navController.navigate(it.destination)
                }
                is SingleEvent.ShowToast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is SingleEvent.ShowSnackBar -> {

                }
            }
        }
    }
}