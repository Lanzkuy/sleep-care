package com.lans.sleep_care.presentation.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    initialHour: Int = 0,
    initialMinute: Int = 0,
    onDismiss: () -> Unit,
    onConfirm: (hour: Int, minute: Int) -> Unit
) {
    val state = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(state.hour, state.minute)
            }) {
                Text(text = stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        title = { Text(text = stringResource(R.string.time_select)) },
        text = {
            TimePicker(
                state = state,
                colors = TimePickerDefaults.colors(
                    clockDialColor = MaterialTheme.colorScheme.secondary,
                    periodSelectorBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    periodSelectorUnselectedContainerColor = MaterialTheme.colorScheme.secondary,
                    periodSelectorSelectedContentColor = White,
                    timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.secondary,
                    timeSelectorSelectedContentColor = White
                )
            )
        }
    )
}