package com.lans.sleep_care.presentation.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.theme.White

@Composable
fun ValidationAlert(
    title: String,
    message: String,
    dismissButtonText: String = stringResource(R.string.cancel),
    confirmButtonText: String = stringResource(R.string.ok),
    onDismiss: () -> Unit,
    onConfirm: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = message) },
        containerColor = White,
        dismissButton = {
            if (onConfirm != null) {
                TextButton(onClick = onDismiss) {
                    Text(text = dismissButtonText)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { (onConfirm ?: onDismiss).invoke() }) {
                Text(text = confirmButtonText)
            }
        }
    )
}