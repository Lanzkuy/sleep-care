package com.lans.sleep_care.presentation.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.logbook.LogbookAnswer
import com.lans.sleep_care.domain.model.logbook.LogbookQuestion
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.lans.sleep_care.presentation.component.form.GenericDropDown
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.White

@Composable
fun CommittedActionDialog(
    week: String,
    areas: List<String>,
    questions: List<LogbookQuestion>,
    answers: List<LogbookQuestionAnswer> = emptyList(),
    onDismiss: () -> Unit,
    onSave: (Boolean, List<LogbookQuestionAnswer>) -> Unit
) {
    var area by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var plan by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var obstacle by remember { mutableStateOf("") }
    var solution by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (answers.isNotEmpty()) {
            area = answers[0].answer.answer
            goal = answers[1].answer.answer
            plan = answers[2].answer.answer
            time = answers[3].answer.answer
            status = answers[4].answer.answer
            obstacle = answers[5].answer.answer
            solution = answers[6].answer.answer
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (answers.isEmpty()) {
                    stringResource(R.string.add_commited_action)

                } else {
                    stringResource(R.string.edit_commited_action)
                }
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(Dimens.dp8)
            ) {
                GenericDropDown(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = stringResource(R.string.choose_area),
                    selected = area,
                    onSelect = { area = it },
                    options = areas
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.goal)) },
                    value = goal,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    ),
                    onValueChange = { goal = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.plan)) },
                    value = plan,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    ),
                    onValueChange = { plan = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.time)) },
                    value = time,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    ),
                    onValueChange = { time = it }
                )
                GenericDropDown(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = stringResource(R.string.choose_status),
                    selected = when (status) {
                        "1" -> "Terlaksana"
                        "0" -> "Belum Terlaksana"
                        else -> ""
                    },
                    onSelect = { status = if (it == "Terlaksana") "1" else "0" },
                    options = listOf("Terlaksana", "Belum Terlaksana")
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.obstacle)) },
                    value = obstacle,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    ),
                    onValueChange = { obstacle = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.how_to_solve)) },
                    value = solution,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    ),
                    onValueChange = { solution = it }
                )
            }
        },
        containerColor = White,
        confirmButton = {
            Button(
                enabled = area.isNotBlank() &&
                    goal.isNotBlank() &&
                    plan.isNotBlank() &&
                    time.isNotBlank() &&
                    status.isNotBlank() &&
                    obstacle.isNotBlank() &&
                    solution.isNotBlank(),
                shape = RoundedLarge,
                onClick = {
                    if (area.isNotBlank() &&
                        goal.isNotBlank() &&
                        plan.isNotBlank() &&
                        time.isNotBlank() &&
                        status.isNotBlank() &&
                        obstacle.isNotBlank() &&
                        solution.isNotBlank()
                    ) {
                        val updatedAnswers = mutableListOf<LogbookQuestionAnswer>()

                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[0].id,
                                answer = answers.getOrNull(0)?.answer?.copy(
                                    answer = area,
                                    note = week
                                ) ?: LogbookAnswer(type = "text", answer = area, note = week)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[1].id,
                                answer = answers.getOrNull(1)?.answer?.copy(
                                    answer = goal,
                                    note = week
                                ) ?: LogbookAnswer(type = "text", answer = goal, note = week)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[2].id,
                                answer = answers.getOrNull(2)?.answer?.copy(
                                    answer = plan,
                                    note = week
                                ) ?: LogbookAnswer(type = "text", answer = plan, note = week)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[3].id,
                                answer = answers.getOrNull(3)?.answer?.copy(
                                    answer = time,
                                    note = week
                                ) ?: LogbookAnswer(type = "text", answer = time, note = week)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[4].id,
                                answer = answers.getOrNull(4)?.answer?.copy(
                                    answer = status,
                                    note = week
                                ) ?: LogbookAnswer(type = "boolean", answer = status, note = week)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[5].id,
                                answer = answers.getOrNull(5)?.answer?.copy(
                                    answer = obstacle,
                                    note = week
                                ) ?: LogbookAnswer(type = "text", answer = obstacle, note = week)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[6].id,
                                answer = answers.getOrNull(6)?.answer?.copy(
                                    answer = solution,
                                    note = week
                                ) ?: LogbookAnswer(type = "text", answer = solution, note = week)
                            )
                        )

                        onSave(answers.isNotEmpty(), updatedAnswers)
                    }
                }
            ) {
                Text(text = stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}