package com.lans.sleep_care.presentation.component.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.lans.sleep_care.presentation.component.misc.StatusChip
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Danger
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.Success
import com.lans.sleep_care.presentation.theme.White

@Composable
fun CommitedActionItem(
    answers: List<LogbookQuestionAnswer>,
    onClick: () -> Unit
) {
    val area = answers[0].answer.answer
    val goal = answers[1].answer.answer
    val plan = answers[2].answer.answer
    val time = answers[3].answer.answer
    val status = answers[4].answer.answer
    val obstacle = answers[5].answer.answer
    val solution = answers[6].answer.answer
    val comment = answers[0].comment

    val color = if (status == "1") Success else Danger
    var expanded by remember { mutableStateOf(false) }

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
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = area,
                        style = MaterialTheme.typography.titleMedium
                    )
                    StatusChip(
                        modifier = Modifier,
                        text = if (status == "1") "Terlaksana" else "Belum Terlaksana",
                        color = color
                    )
                }
                Spacer(modifier = Modifier.height(Dimens.dp8))
                Text(
                    text = goal,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (expanded) {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = Dimens.dp12),
                    thickness = Dimens.dp1,
                    color = Gray
                )
                InfoSection(
                    Icons.AutoMirrored.Filled.EventNote,
                    stringResource(R.string.plan),
                    plan
                )
                InfoSection(Icons.Default.Schedule, stringResource(R.string.time), time)
                InfoSection(Icons.Default.Block, stringResource(R.string.obstacle), obstacle)
                InfoSection(Icons.Default.Lightbulb, stringResource(R.string.solution), solution)
            }
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp12)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { expanded = !expanded }) {
                    Text(text = if (expanded) "Tutup" else "Lihat Detail")
                }
            }
            if(comment.isNotEmpty()) {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(
                            vertical = Dimens.dp8
                        )
                )
                Text(
                    text = "Komentar",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Black,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = answers[0].comment,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Black
                    )
                )
            }
        }
    }
}

@Composable
fun InfoSection(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .padding(vertical = Dimens.dp6),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            modifier = Modifier
                .size(Dimens.dp24)
                .padding(end = Dimens.dp8),
            imageVector = icon,
            tint = DarkGray,
            contentDescription = label
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
    }
}