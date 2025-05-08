package com.lans.sleep_care.presentation.component.items

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.lans.sleep_care.domain.model.CommittedAction
import com.lans.sleep_care.presentation.component.misc.StatusChip
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.White

@Composable
fun CommitedActionItem(
    record: CommittedAction
) {
    val color = when (record.status.lowercase()) {
        "selesai", "berhasil" -> Color(0xFF4CAF50)
        "sedang berjalan" -> Color(0xFFFFC107)
        else -> Color(0xFFF44336)
    }
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.dp6),
        shape = RoundedLarge
    ) {
        Column(
            modifier = Modifier
                .padding(Dimens.dp16)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = record.area,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(
                        modifier = Modifier
                            .height(Dimens.dp4)
                    )
                    Text(
                        text = record.goal,
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                StatusChip(
                    modifier = Modifier
                        .padding(
                            top = Dimens.dp16,
                            end = Dimens.dp16
                        ),
                    text = record.status,
                    color = color,
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
                    record.plan
                )
                InfoSection(Icons.Default.Schedule, stringResource(R.string.time), record.time)
                InfoSection(Icons.Default.Block, stringResource(R.string.obstacle), record.obstacle)
                InfoSection(
                    Icons.Default.Lightbulb,
                    stringResource(R.string.solution), record.solution
                )
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
                .size(Dimens.dp20)
                .padding(end = Dimens.dp8),
            imageVector = icon,
            tint = DarkGray,
            contentDescription = label
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
    }
}