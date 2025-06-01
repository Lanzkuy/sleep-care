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

@Composable
fun CommittedActionDialog(
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
                    onValueChange = { goal = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.plan)) },
                    value = plan,
                    onValueChange = { plan = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.time)) },
                    value = time,
                    onValueChange = { time = it }
                )
                GenericDropDown(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = stringResource(R.string.choose_status),
                    selected = if (status == "0") "Terlaksana" else "Belum Terlaksana",
                    onSelect = { status = if (it == "Terlaksana") "0" else "1" },
                    options = listOf("Terlaksana", "Belum Terlaksana")
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.obstacle)) },
                    value = obstacle,
                    onValueChange = { obstacle = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.how_to_solve)) },
                    value = solution,
                    onValueChange = { solution = it }
                )
            }
        },
        confirmButton = {
            Button(
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
                                answer = answers.getOrNull(0)?.answer?.copy(answer = area)
                                    ?: LogbookAnswer(type = "text", answer = area)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[1].id,
                                answer = answers.getOrNull(1)?.answer?.copy(answer = goal)
                                    ?: LogbookAnswer(type = "text", answer = goal)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[2].id,
                                answer = answers.getOrNull(2)?.answer?.copy(answer = plan)
                                    ?: LogbookAnswer(type = "text", answer = plan)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[3].id,
                                answer = answers.getOrNull(3)?.answer?.copy(answer = time)
                                    ?: LogbookAnswer(type = "text", answer = time)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[4].id,
                                answer = answers.getOrNull(4)?.answer?.copy(answer = status)
                                    ?: LogbookAnswer(type = "boolean", answer = status)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[5].id,
                                answer = answers.getOrNull(5)?.answer?.copy(answer = obstacle)
                                    ?: LogbookAnswer(type = "text", answer = obstacle)
                            )
                        )
                        updatedAnswers.add(
                            LogbookQuestionAnswer(
                                questionId = questions[6].id,
                                answer = answers.getOrNull(6)?.answer?.copy(answer = solution)
                                    ?: LogbookAnswer(type = "text", answer = solution)
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