package com.lans.sleep_care.presentation.component.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.logbook.LogbookAnswer
import com.lans.sleep_care.domain.model.logbook.LogbookQuestion
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.lans.sleep_care.presentation.component.form.GenericDropDown
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.White
import kotlin.math.roundToInt

@Composable
fun EmotionRecordDialog(
    dateRange: Pair<String, String>,
    emotions: List<String>,
    questions: List<LogbookQuestion>,
    answers: List<LogbookQuestionAnswer> = emptyList(),
    onDismiss: () -> Unit,
    onSave: (Boolean, List<LogbookQuestionAnswer>) -> Unit
) {
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var situation by remember { mutableStateOf("") }
    var thought by remember { mutableStateOf("") }
    var emotion by remember { mutableStateOf("") }
    var intensityBefore by remember { mutableFloatStateOf(1f) }
    var manage by remember { mutableStateOf("") }
    var intensityAfter by remember { mutableFloatStateOf(1f) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            dateRange = dateRange.first to dateRange.second,
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
            thought = answers[3].answer.answer
            emotion = answers[4].answer.answer
            intensityBefore = answers[5].answer.answer.toFloat()
            manage = answers[6].answer.answer
            intensityAfter = answers[7].answer.answer.toFloat()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (answers.isEmpty()) {
                    stringResource(R.string.add_emotion_record)

                } else {
                    stringResource(R.string.edit_emotion_record)
                }
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true }
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = { Text(text = stringResource(R.string.date)) },
                        value = date,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        enabled = false,
                        readOnly = true,
                        colors = TextFieldDefaults.colors(
                            disabledIndicatorColor = Black,
                            disabledContainerColor = White,
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
                        label = { Text(text = stringResource(R.string.time)) },
                        value = time,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        enabled = false,
                        readOnly = true,
                        colors = TextFieldDefaults.colors(
                            disabledIndicatorColor = Black,
                            disabledContainerColor = White,
                            disabledLabelColor = TextFieldDefaults.colors().unfocusedLabelColor,
                            disabledTextColor = Black
                        ),
                        onValueChange = { time = it }
                    )
                }
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.what_happened)) },
                    value = situation,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    ),
                    onValueChange = { situation = it }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.what_comes_to_mind)) },
                    value = thought,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    ),
                    onValueChange = { thought = it }
                )
                Spacer(
                    modifier = Modifier
                        .height(Dimens.dp12)
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = Dimens.dp4),
                    text = stringResource(R.string.emotion_felt),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                GenericDropDown(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = stringResource(R.string.choose_emotion),
                    selected = emotion,
                    onSelect = { emotion = it },
                    options = emotions
                )
                Text(
                    modifier = Modifier
                        .padding(top = Dimens.dp8),
                    text = "${stringResource(R.string.intensity)}: ${intensityBefore.roundToInt()}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Slider(
                    value = intensityBefore,
                    valueRange = 1f..10f,
                    steps = 8,
                    onValueChange = { intensityBefore = it }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.what_do_you_do)) },
                    value = manage,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    ),
                    onValueChange = { manage = it }
                )
                Text(
                    modifier = Modifier
                        .padding(top = Dimens.dp8),
                    text = "${stringResource(R.string.intensity_after)}: ${intensityAfter.roundToInt()}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Slider(
                    value = intensityAfter,
                    valueRange = 1f..10f,
                    steps = 8,
                    onValueChange = { intensityAfter = it }
                )
            }
        },
        containerColor = White,
        confirmButton = {
            Button(
                shape = RoundedLarge,
                onClick = {
                    if (date.isNotBlank() &&
                        time.isNotBlank() &&
                        situation.isNotBlank() &&
                        thought.isNotEmpty() &&
                        emotion.isNotEmpty() &&
                        manage.isNotEmpty()
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
                                answer = answers.getOrNull(3)?.answer?.copy(answer = thought)
                                    ?: LogbookAnswer(type = "text", answer = thought)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[4].id,
                                answer = answers.getOrNull(4)?.answer?.copy(answer = emotion)
                                    ?: LogbookAnswer(type = "text", answer = emotion)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[5].id,
                                answer = answers.getOrNull(5)?.answer?.copy(
                                    answer = intensityBefore.toInt().toString()
                                ) ?: LogbookAnswer(
                                    type = "number",
                                    answer = intensityBefore.toInt().toString()
                                )
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[6].id,
                                answer = answers.getOrNull(6)?.answer?.copy(answer = manage)
                                    ?: LogbookAnswer(type = "text", answer = manage)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[7].id,
                                answer = answers.getOrNull(7)?.answer?.copy(
                                    answer = intensityAfter.toInt().toString()
                                ) ?: LogbookAnswer(
                                    type = "number",
                                    answer = intensityAfter.toInt().toString()
                                )
                            )
                        )

                        onSave(answers.isNotEmpty(), updatedAnswers)
                    }
                })
            {
                Text(text = stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}