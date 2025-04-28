package com.lans.sleep_care.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.EmotionRecord
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.White

@Composable
fun EmotionRecordItem(record: EmotionRecord) {
    val emotionColor = when (record.emotion) {
        "Senang" -> Color(0xFF81C784)
        "Sedih" -> Color(0xFF64B5F6)
        "Marah" -> Color(0xFFE57373)
        "Takut" -> Color(0xFFBA68C8)
        "Cemas" -> Color(0xFFFFB74D)
        "Netral" -> Color(0xFFBDBDBD)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.dp12,
                vertical = Dimens.dp6
            ),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.dp2),
        shape = RoundedLarge
    ) {
        Column(
            modifier = Modifier
                .padding(Dimens.dp16)
        ) {
            Text(
                text = record.date,
                color = Color.Gray,
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                modifier = Modifier.padding(
                    top = Dimens.dp4,
                    bottom = Dimens.dp8
                ),
                text = "${record.emotion} (${record.intensity}/10)",
                color = emotionColor,
                style = MaterialTheme.typography.titleMedium,
            )
            record.situation?.let {
                EmotionRecordRow(
                    label = stringResource(R.string.situation),
                    value = it
                )
            }
            record.thoughts?.let {
                EmotionRecordRow(
                    label = stringResource(R.string.thought),
                    value = it
                )
            }
            record.copingStrategy?.let {
                EmotionRecordRow(
                    label = stringResource(R.string.how_to_manage),
                    value = it
                )
            }
            record.emotionAfter?.let {
                EmotionRecordRow(
                    label = stringResource(R.string.afterwards),
                    value = "$it (${record.intensityAfter ?: "-"}/10)"
                )
            }
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
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            color = Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}