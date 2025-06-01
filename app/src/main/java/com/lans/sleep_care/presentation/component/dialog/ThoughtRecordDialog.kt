package com.lans.sleep_care.presentation.component.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.logbook.LogbookAnswer
import com.lans.sleep_care.domain.model.logbook.LogbookQuestion
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.Secondary
import com.lans.sleep_care.utils.toList

@Composable
fun ThoughtRecordDialog(
    questions: List<LogbookQuestion>,
    answers: List<LogbookQuestionAnswer> = emptyList(),
    onDismiss: () -> Unit,
    onSave: (Boolean, List<LogbookQuestionAnswer>) -> Unit
) {
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var situation by remember { mutableStateOf("") }
    var currentThought by remember { mutableStateOf("") }
    val thoughts = remember { mutableStateListOf<String>() }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var editingIndex by remember { mutableStateOf<Int?>(null) }

    if (showDatePicker) {
        DatePickerDialog(
            onDismiss = { showDatePicker = false },
            onConfirm = { selectedDate ->
                showDatePicker = false
                date = selectedDate
            }
        )
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismiss = { showTimePicker = false },
            onConfirm = { selectedTime ->
                showTimePicker = false
                time = selectedTime
            }
        )
    }

    LaunchedEffect(Unit) {
        if (answers.isNotEmpty()) {
            date = answers[0].answer.answer
            time = answers[1].answer.answer
            situation = answers[2].answer.answer
            thoughts.clear()
            thoughts.addAll(answers[3].answer.answer.toList())
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (answers.isEmpty()) {
                    stringResource(R.string.add_thought_record)
                } else {
                    stringResource(R.string.edit_thought_record)
                }
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.dp8)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true }
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = {
                            Text(
                                text = stringResource(R.string.date),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        value = date,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        enabled = false,
                        readOnly = true,
                        colors = TextFieldDefaults.colors(
                            disabledIndicatorColor = Gray,
                            disabledContainerColor = Color.Transparent,
                            disabledLabelColor = TextFieldDefaults.colors().unfocusedLabelColor,
                            disabledTextColor = Black
                        ),
                        onValueChange = { date = it }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTimePicker = true }
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = {
                            Text(
                                text = stringResource(R.string.time),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        value = time,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        enabled = false,
                        readOnly = true,
                        colors = TextFieldDefaults.colors(
                            disabledIndicatorColor = Gray,
                            disabledContainerColor = Color.Transparent,
                            disabledLabelColor = TextFieldDefaults.colors().unfocusedLabelColor,
                            disabledTextColor = Black
                        ),
                        onValueChange = { time = it }
                    )
                }
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
                    horizontalArrangement = Arrangement.spacedBy(Dimens.dp4),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        label = { Text(stringResource(R.string.thought)) },
                        value = currentThought,
                        onValueChange = { currentThought = it }
                    )
                    IconButton(
                        onClick = {
                            if (currentThought.isNotBlank()) {
                                editingIndex?.let {
                                    thoughts[it] = currentThought
                                    editingIndex = null
                                } ?: run {
                                    thoughts.add(currentThought)
                                }
                                currentThought = ""
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (editingIndex != null) Icons.Default.Check else Icons.Default.AddCircle,
                            contentDescription = stringResource(R.string.icon)
                        )
                    }
                }
                if (thoughts.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = Dimens.dp150),
                        verticalArrangement = Arrangement.spacedBy(Dimens.dp4)
                    ) {
                        itemsIndexed(thoughts) { index, thought ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Secondary),
                                shape = RoundedLarge
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(Dimens.dp8)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .weight(1f),
                                        text = thought,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(Dimens.dp4)
                                    ) {
                                        IconButton(
                                            modifier = Modifier
                                                .size(Dimens.dp24),
                                            onClick = {
                                                currentThought = thought
                                                editingIndex = index
                                            }
                                        ) {
                                            Icon(
                                                modifier = Modifier
                                                    .size(Dimens.dp24),
                                                imageVector = Icons.Default.Edit,
                                                tint = DarkGray,
                                                contentDescription = stringResource(R.string.icon)
                                            )
                                        }
                                        IconButton(
                                            modifier = Modifier
                                                .size(Dimens.dp24),
                                            onClick = {
                                                thoughts.removeAt(index)
                                                if (editingIndex == index) {
                                                    editingIndex = null
                                                    currentThought = ""
                                                }
                                            }
                                        ) {
                                            Icon(
                                                modifier = Modifier
                                                    .size(Dimens.dp24),
                                                imageVector = Icons.Default.Delete,
                                                tint = DarkGray,
                                                contentDescription = stringResource(R.string.icon)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                shape = RoundedLarge,
                onClick = {
                    if (date.isNotBlank() &&
                        time.isNotBlank() &&
                        situation.isNotBlank() &&
                        thoughts.isNotEmpty()
                    ) {
                        val updatedAnswers = mutableListOf<LogbookQuestionAnswer>()

                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[0].id,
                                answer = answers.getOrNull(0)?.answer?.copy(answer = date)
                                    ?: LogbookAnswer(type = "date", answer = date)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[1].id,
                                answer = answers.getOrNull(1)?.answer?.copy(answer = time)
                                    ?: LogbookAnswer(type = "time", answer = time)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[2].id,
                                answer = answers.getOrNull(2)?.answer?.copy(answer = situation)
                                    ?: LogbookAnswer(type = "text", answer = situation)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[3].id,
                                answer = answers.getOrNull(3)?.answer?.copy(
                                    answer = thoughts.toList().toString()
                                ) ?: LogbookAnswer(
                                    type = "text",
                                    answer = thoughts.toList().toString()
                                )
                            )
                        )

                        onSave(answers.isNotEmpty(), updatedAnswers)
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