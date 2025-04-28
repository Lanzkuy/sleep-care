package com.lans.sleep_care.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.DiaryAnswer
import com.lans.sleep_care.domain.model.DiaryQuestion
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.Secondary
import com.lans.sleep_care.utils.getDayName

@Composable
fun SleepDiary(
    dates: List<String>,
    questions: Pair<List<DiaryQuestion>, List<DiaryQuestion>>,
    answers: List<DiaryAnswer>,
    onAnswerChanged: (date: String, questionId: Int, newAnswer: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(dates) { date ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.dp8),
                colors = CardDefaults.cardColors(containerColor = Gray),
                shape = RoundedLarge
            ) {
                Column(
                    modifier = Modifier
                        .padding(Dimens.dp16)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = Dimens.dp8),
                        text = getDayName(date),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = Dimens.sp20
                        )
                    )
                    Text(
                        text = stringResource(R.string.day),
                        color = Secondary,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    questions.first.forEach { question ->
                        val answer =
                            answers.find { it.questionId == question.id && it.date == date }
                                ?.copy(
                                    subAnswers = question.subQuestions.map { subQuestion ->
                                        answers.find { it.questionId == subQuestion.id && it.date == date }
                                            ?: DiaryAnswer(
                                                date = date,
                                                questionId = subQuestion.id,
                                                value = "",
                                                subAnswers = emptyList()
                                            )
                                    }
                                ) ?: DiaryAnswer(
                                date = date,
                                questionId = question.id,
                                value = "",
                                subAnswers = question.subQuestions.map {
                                    answers.find { answer -> answer.questionId == it.id && answer.date == date }
                                        ?: DiaryAnswer(
                                            date = date,
                                            questionId = it.id,
                                            value = "",
                                            subAnswers = emptyList()
                                        )
                                }
                            )

                        DiaryQuestionItem(
                            question = question,
                            answer = answer,
                            onAnswerChanged = { questionId, newAnswer ->
                                onAnswerChanged(date, questionId, newAnswer)
                            }
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = Dimens.dp1)
                    }
                    Spacer(
                        modifier = Modifier
                            .height(Dimens.dp12)
                    )
                    Text(
                        text = stringResource(R.string.night),
                        color = Secondary,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    questions.second.forEach { question ->
                        val answer =
                            answers.find { it.questionId == question.id && it.date == date }
                                ?.copy(
                                    subAnswers = question.subQuestions.map { subQuestion ->
                                        answers.find { it.questionId == subQuestion.id && it.date == date }
                                            ?: DiaryAnswer(
                                                date = date,
                                                questionId = subQuestion.id,
                                                value = "",
                                                subAnswers = emptyList()
                                            )
                                    }
                                ) ?: DiaryAnswer(
                                date = date,
                                questionId = question.id,
                                value = "",
                                subAnswers = question.subQuestions.map {
                                    answers.find { answer -> answer.questionId == it.id && answer.date == date }
                                        ?: DiaryAnswer(
                                            date = date,
                                            questionId = it.id,
                                            value = "",
                                            subAnswers = emptyList()
                                        )
                                }
                            )

                        DiaryQuestionItem(
                            question = question,
                            answer = answer,
                            onAnswerChanged = { questionId, newAnswer ->
                                onAnswerChanged(date, questionId, newAnswer)
                            }
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = Dimens.dp1)
                    }
                }
            }
        }
    }
}