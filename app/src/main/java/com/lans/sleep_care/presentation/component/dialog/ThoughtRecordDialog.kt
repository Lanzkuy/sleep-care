package com.lans.sleep_care.presentation.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.logbook.ThoughtRecord
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.RoundedLarge

@Composable
fun ThoughtRecordDialog(
    onDismiss: () -> Unit,
    onSave: (ThoughtRecord) -> Unit
) {
    var date by remember { mutableStateOf("") }
    var situation by remember { mutableStateOf("") }
    var currentThought by remember { mutableStateOf("") }
    val thoughts = remember { mutableStateListOf<String>() }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.add_thought_record)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.dp8)) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(R.string.date_yyyy_mm_dd),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    value = date,
                    onValueChange = { date = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(R.string.situation),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    value = situation,
                    onValueChange = { situation = it }
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.dp8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        label = {
                            Text(
                                text = stringResource(R.string.thought),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        value = currentThought,
                        onValueChange = { currentThought = it }
                    )
                    IconButton(
                        onClick = {
                            if (currentThought.isNotBlank()) {
                                thoughts.add(currentThought)
                                currentThought = ""
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = stringResource(R.string.icon)
                        )
                    }
                }
                thoughts.forEach {
                    Text(
                        text = "• $it",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                shape = RoundedLarge,
                onClick = {
                    if (date.isNotBlank() && situation.isNotBlank() && thoughts.isNotEmpty()) {
                        onSave(
                            ThoughtRecord(
                                date = date,
                                situation = situation,
                                thoughts = thoughts.toList()
                            )
                        )
                    }
                }
            ) {
                Text(stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}