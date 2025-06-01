package com.lans.sleep_care.presentation.component.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.White

@Composable
fun EmotionRecordItem(
    answers: List<LogbookQuestionAnswer>,
    onClick: () -> Unit
) {
    val date = answers[0].answer.answer
    val time = answers[1].answer.answer
    val situation = answers[2].answer.answer
    val thought = answers[3].answer.answer
    val emotion = answers[4].answer.answer
    val intensityBefore = answers[5].answer.answer
    val manage = answers[6].answer.answer
    val intensityAfter = answers[7].answer.answer

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            },
        border = BorderStroke(
            width = Dimens.dp1,
            color = Gray
        ),
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RoundedLarge
    ) {
        Column(
            modifier = Modifier
                .padding(Dimens.dp16)
        ) {
            Text(
                text = "$date $time",
                color = Color.Gray,
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                modifier = Modifier.padding(
                    top = Dimens.dp4,
                    bottom = Dimens.dp8
                ),
                text = "$emotion (${intensityBefore}/10)",
                color = DarkGray,
                style = MaterialTheme.typography.titleMedium,
            )
            EmotionRecordRow(
                label = stringResource(R.string.situation),
                value = situation
            )
            EmotionRecordRow(
                label = stringResource(R.string.thought),
                value = thought
            )
            EmotionRecordRow(
                label = stringResource(R.string.how_to_manage),
                value = manage
            )
            EmotionRecordRow(
                label = stringResource(R.string.afterwards),
                value = "$emotion (${intensityAfter}/10)"
            )
        }
    }
}

@Composable
fun EmotionRecordRow(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .padding(bottom = Dimens.dp8)
    ) {
        Text(
            text = label,
            color = DarkGray,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = value,
            color = Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}