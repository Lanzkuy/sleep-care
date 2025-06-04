package com.lans.sleep_care.presentation.component.dialog

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.theme.White
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    dateRange: Pair<String, String>,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val formatter = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }

    val rangeStart = formatter.parse(dateRange.first)?.time ?: 0L
    val rangeEnd = formatter.parse(dateRange.second)?.time ?: Long.MAX_VALUE

    val customSelectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis in rangeStart..rangeEnd
        }
    }

    val state = rememberDatePickerState(
        initialSelectedDateMillis = rangeStart,
        selectableDates = customSelectableDates
    )

    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            containerColor = White
        ),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val selectedDate = state.selectedDateMillis?.let {
                    val date = Date(it)
                    formatter.format(date)
                }
                onConfirm(selectedDate ?: "")
            }) {
                Text(text = stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(
            state = state,
            showModeToggle = true,
            colors = DatePickerDefaults.colors(
                containerColor = White
            )
        )
    }
}