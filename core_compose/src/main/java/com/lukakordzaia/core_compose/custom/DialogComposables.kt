package com.lukakordzaia.core_compose.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lukakordzaia.core.R

@Composable
fun ProgressDialog(
    showDialog: Boolean,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {},
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment= Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator(
                    strokeWidth = 5.dp,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

@Composable
fun CommonDialog(
    showDialog: Boolean,
    onDismiss: (Boolean) -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss(true) }
        ) {
            Column(
                modifier = Modifier
                    .size(80.dp)
            ) {
                Text(text = stringResource(id = R.string.common_error))
                Button(onClick = { onDismiss(true) }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        }
    }
}

@Composable
fun QuestionDialog(
    showDialog: Boolean,
    question: String = stringResource(id = R.string.are_you_sure),
    noButtonText: String = stringResource(id = R.string.cancel_text),
    yestButtonText: String = stringResource(id = R.string.yes),
    onDismiss: (Boolean) -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss(false) }
        ) {
           Column(
               modifier = Modifier
                   .padding(20.dp)
                   .fillMaxWidth()
                   .background(Color.White, shape = RoundedCornerShape(8.dp))
           ) {
               BoldText(
                   modifier = Modifier
                       .padding(20.dp)
                       .align(Alignment.CenterHorizontally),
                   text = question,
                   fontSize = 16.sp,
                   color = MaterialTheme.colors.onPrimary
               )
               Row(
                   modifier = Modifier
                       .padding(top = 50.dp)
               ) {
                   Button(
                       modifier = Modifier
                           .padding(5.dp),
                       onClick = { onDismiss(false) },
                       elevation = null
                   ) {
                       LightText(
                           modifier = Modifier
                               .padding(5.dp),
                           text = noButtonText,
                           fontWeight = FontWeight.Bold,
                           fontSize = 15.sp
                       )
                   }
                   Button(
                       modifier = Modifier
                           .padding(5.dp)
                           .fillMaxWidth(),
                       onClick = { onConfirm.invoke() },
                       colors = ButtonDefaults.buttonColors(
                           backgroundColor = MaterialTheme.colors.secondary,
                           contentColor = MaterialTheme.colors.onSecondary
                       )
                   ) {
                       LightText(
                           modifier = Modifier
                               .padding(5.dp),
                           text = yestButtonText,
                           fontWeight = FontWeight.Bold,
                           fontSize = 15.sp,
                           color = MaterialTheme.colors.onSecondary
                       )
                   }
               }
           }
        }
    }
}

@Preview
@Composable
fun QuestionDialogPreview() {
    QuestionDialog(showDialog = true, onDismiss = {}) {}
}