package com.lans.sleep_care.presentation.component.form

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.logbook.LogbookAnswer
import com.lans.sleep_care.domain.model.logbook.LogbookQuestion
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.lans.sleep_care.presentation.component.items.DiaryQuestionItem
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.utils.parseToDayName

@Composable
fun SleepDiary(
    dateWithIds: List<Pair<Int, String>>,
    questions: List<LogbookQuestion>,
    answers: List<List<LogbookQuestionAnswer>>,
    expandedStates: MutableMap<String, Boolean>,
    onAnswerChanged: (Int, LogbookQuestionAnswer) -> Unit
) {
    if (questions.isEmpty()) return

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(dateWithIds) { (recordId, date) ->
            val dateIndex = dateWithIds.indexOf(Pair(recordId, date))
            val dayAnswers = answers.getOrNull(dateIndex).orEmpty()
            val isExpanded = expandedStates[date] ?: false

            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.dp8),
                border = BorderStroke(width = Dimens.dp1, color = Gray),
                shape = RoundedLarge
            ) {
                Column(modifier = Modifier.padding(Dimens.dp16)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedStates[date] = !isExpanded
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = parseToDayName(date),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = Dimens.sp18,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Icon(
                            imageVector = if (isExpanded) {
                                Icons.Default.ExpandLess
                            } else Icons.Default.ExpandMore,
                            contentDescription = stringResource(R.string.icon)
                        )
                    }

                    AnimatedVisibility(visible = isExpanded) {
                        Column(
                            modifier = Modifier
                                .padding(top = Dimens.dp8)
                        ) {
                            DiaryGroup(
                                title = stringResource(R.string.day),
                                recordId = recordId,
                                questions = questions,
                                noteType = "Siang",
                                answers = dayAnswers,
                                onAnswerChanged = onAnswerChanged
                            )

                            Spacer(modifier = Modifier.height(Dimens.dp12))

                            DiaryGroup(
                                title = stringResource(R.string.night),
                                recordId = recordId,
                                questions = questions,
                                noteType = "Malam",
                                answers = dayAnswers,
                                onAnswerChanged = onAnswerChanged
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DiaryGroup(
    title: String,
    recordId: Int,
    questions: List<LogbookQuestion>,
    noteType: String,
    answers: List<LogbookQuestionAnswer>,
    onAnswerChanged: (Int, LogbookQuestionAnswer) -> Unit
) {
    Text(
        text = title,
        color = Primary,
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
    )

    questions.filter { it.note == noteType && it.parentId == -1 }
        .forEach { question ->
            val subQuestions = questions
                .filter { it.parentId == question.id }
                .sortedBy { it.id }

            val answer = answers.find { it.questionId == question.id }?.answer
                ?: LogbookAnswer(id = 0, type = question.type, answer = "", note = noteType)

            val subAnswers = subQuestions.map { subQuestion ->
                answers.find { it.questionId == subQuestion.id }?.answer
                    ?: LogbookAnswer(id = 0, type = subQuestion.type, answer = "", note = noteType)
            }

            DiaryQuestionItem(
                recordId = recordId,
                question = question,
                answer = answer,
                subQuestions = subQuestions,
                subAnswers = subAnswers,
                onAnswerChanged = onAnswerChanged
            )

            HorizontalDivider(color = Color.LightGray, thickness = Dimens.dp1)
        }
}