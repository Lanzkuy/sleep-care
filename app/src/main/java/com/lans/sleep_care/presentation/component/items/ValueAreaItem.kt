package com.lans.sleep_care.presentation.component.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.domain.model.logbook.LogbookAnswer
import com.lans.sleep_care.domain.model.logbook.LogbookQuestion
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.White
import kotlin.math.roundToInt

@Composable
fun ValueAreaItem(
    areaName: String,
    questions: List<LogbookQuestion>,
    answers: List<LogbookQuestionAnswer>,
    onDataChange: (LogbookQuestionAnswer) -> Unit
) {
    if (questions.isEmpty()) return

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(width = Dimens.dp1, color = Gray),
        colors = CardDefaults.outlinedCardColors(containerColor = White),
        shape = RoundedLarge
    ) {
        Column(modifier = Modifier.padding(Dimens.dp16)) {
            Text(
                text = areaName,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            questions.forEach { question ->
                val questionAnswer = answers.find { it.questionId == question.id }
                val answer = questionAnswer?.answer?.answer

                var scale by rememberSaveable(question.id to answer) {
                    mutableFloatStateOf(answer?.toFloatOrNull() ?: 1f)
                }
                var text by rememberSaveable(question.id to answer) {
                    mutableStateOf(answer ?: "")
                }

                Column(modifier = Modifier.padding(top = Dimens.dp8)) {
                    when (question.type) {
                        "number" -> {
                            Text(
                                text = "${question.question}: ${scale.roundToInt()}",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Slider(
                                value = scale,
                                valueRange = 1f..10f,
                                steps = 8,
                                onValueChange = {
                                    scale = it
                                    onDataChange(
                                        questionAnswer?.copy(
                                            questionId = question.id,
                                            answer = questionAnswer.answer.copy(
                                                answer = it.roundToInt().toString(),
                                                note = areaName
                                            )
                                        ) ?: LogbookQuestionAnswer(
                                            questionId = question.id,
                                            answer = LogbookAnswer(
                                                type = question.type,
                                                answer = it.roundToInt().toString(),
                                                note = areaName
                                            )
                                        )
                                    )
                                }
                            )
                        }

                        "text" -> {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                label = {
                                    Text(
                                        text = question.question,
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                },
                                value = text,
                                onValueChange = {
                                    text = it
                                    onDataChange(
                                        questionAnswer?.copy(
                                            questionId = question.id,
                                            answer = questionAnswer.answer.copy(
                                                answer = it,
                                                note = areaName
                                            )
                                        ) ?: LogbookQuestionAnswer(
                                            questionId = question.id,
                                            answer = LogbookAnswer(
                                                type = question.type,
                                                answer = it,
                                                note = areaName
                                            )
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}