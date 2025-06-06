package com.lans.sleep_care.presentation.component.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.logbook.LogbookAnswer
import com.lans.sleep_care.domain.model.logbook.LogbookQuestion
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.lans.sleep_care.presentation.component.dialog.TimePickerDialog
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded

@Composable
fun DiaryQuestionItem(
    recordId: Int,
    question: LogbookQuestion,
    answer: LogbookAnswer,
    subQuestions: List<LogbookQuestion>,
    subAnswers: List<LogbookAnswer>,
    isReadOnly: Boolean,
    onAnswerChanged: (Int, LogbookQuestionAnswer) -> Unit
) {
    var text by rememberSaveable(answer.id to answer.answer) {
        mutableStateOf(answer.answer)
    }

    Column(modifier = Modifier.padding(vertical = Dimens.dp4)) {
        Text(
            text = question.question,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        when (question.type) {
            "boolean" -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = Dimens.dp4)
                            .clip(Rounded)
                            .background(if (text == "1") Primary.copy(alpha = 0.2f) else Color.Transparent)
                    ) {
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isReadOnly,
                            onClick = {
                                text = "1"
                                onAnswerChanged(
                                    recordId,
                                    LogbookQuestionAnswer(
                                        questionId = question.id,
                                        answer = answer.copy(
                                            answer = "1"
                                        )
                                    )
                                )
                            }
                        ) {
                            Text(text = stringResource(R.string.yes))
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = Dimens.dp4)
                            .clip(Rounded)
                            .background(if (text == "0") Primary.copy(alpha = 0.2f) else Color.Transparent)
                    ) {
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isReadOnly,
                            onClick = {
                                text = "0"
                                onAnswerChanged(
                                    recordId,
                                    LogbookQuestionAnswer(
                                        questionId = question.id,
                                        answer = answer.copy(
                                            answer = "0"
                                        )
                                    )
                                )
                            }
                        ) {
                            Text(text = stringResource(R.string.no))
                        }
                    }
                }
            }

            "time" -> {
                var showPicker by remember { mutableStateOf(false) }

                if (showPicker && !isReadOnly) {
                    TimePickerDialog(
                        onDismiss = { showPicker = false },
                        onConfirm = { selectedTime ->
                            showPicker = false
                            text = selectedTime

                            onAnswerChanged(
                                recordId,
                                LogbookQuestionAnswer(
                                    questionId = question.id,
                                    answer = answer.copy(answer = selectedTime)
                                )
                            )
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showPicker = true }
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = text,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        enabled = false,
                        readOnly = true,
                        colors = TextFieldDefaults.colors(
                            disabledIndicatorColor = Black,
                            disabledContainerColor = Color.Transparent,
                            disabledLabelColor = TextFieldDefaults.colors().unfocusedLabelColor,
                            disabledTextColor = Black
                        ),
                        onValueChange = { }
                    )
                }
            }

            else -> {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = text,
                    readOnly = isReadOnly,
                    onValueChange = {
                        text = it
                        onAnswerChanged(
                            recordId,
                            LogbookQuestionAnswer(
                                questionId = question.id,
                                answer = answer.copy(
                                    answer = it
                                )
                            )
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (subQuestions.isNotEmpty() && text == "1") {
            Column(modifier = Modifier.padding(start = Dimens.dp16)) {
                subQuestions.zip(subAnswers).forEach { (subQuestion, subAnswer) ->
                    DiaryQuestionItem(
                        recordId = recordId,
                        question = subQuestion,
                        answer = subAnswer,
                        subQuestions = emptyList(),
                        subAnswers = emptyList(),
                        isReadOnly = isReadOnly,
                        onAnswerChanged = onAnswerChanged
                    )
                }
            }
        }
    }
}