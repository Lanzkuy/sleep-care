package com.lans.sleep_care.presentation.component

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.DiaryAnswer
import com.lans.sleep_care.domain.model.DiaryQuestion
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded

@Composable
fun DiaryQuestionItem(
    question: DiaryQuestion,
    answer: DiaryAnswer,
    onAnswerChanged: (questionId: Int, newAnswer: String) -> Unit
) {
    var text by rememberSaveable(answer.questionId to answer.value) {
        mutableStateOf(answer.value)
    }

    Column(
        modifier = Modifier
            .padding(vertical = Dimens.dp4)
    ) {
        Text(
            text = question.text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        if (question.isYesNo) {
            val currentAnswer = answer.value
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = Dimens.dp4)
                        .clip(Rounded)
                        .background(
                            if (currentAnswer == "Ya") {
                                Primary.copy(alpha = 0.2f)
                            } else Color.Transparent
                        )
                ) {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            text = "Ya"
                            onAnswerChanged(question.id, text)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.yes),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = Dimens.dp4)
                        .clip(Rounded)
                        .background(
                            if (currentAnswer == stringResource(R.string.no)) {
                                Primary.copy(alpha = 0.2f)
                            } else Color.Transparent
                        )
                ) {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            text = "Tidak"
                            onAnswerChanged(question.id, text)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.no),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        } else {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = text,
                textStyle = MaterialTheme.typography.bodyMedium,
                onValueChange = {
                    text = it
                    onAnswerChanged(question.id, it)
                }
            )
        }
        if (question.subQuestions.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(start = Dimens.dp16)
            ) {
                question.subQuestions.zip(answer.subAnswers).forEach { (subQuestion, subAnswer) ->
                    DiaryQuestionItem(
                        question = subQuestion,
                        answer = subAnswer,
                        onAnswerChanged = onAnswerChanged
                    )
                }
            }
        }
    }
}