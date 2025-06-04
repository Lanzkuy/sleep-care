package com.lans.sleep_care.presentation.component.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.therapy.TherapySchedule
import com.lans.sleep_care.presentation.component.misc.StatusChip
import com.lans.sleep_care.presentation.theme.Danger
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.MediumGray
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.Secondary
import com.lans.sleep_care.presentation.theme.Success
import com.lans.sleep_care.utils.formatToTime

@Composable
fun HistoryTherapySessionItem(
    schedule: TherapySchedule,
    onClick: () -> Unit
) {
    val color = if (schedule.note.isEmpty()) {
        Danger
    } else {
        Success
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.dp8),
        shape = RoundedLarge
    ) {
        Column(
            modifier = Modifier
                .padding(Dimens.dp16)
        ) {
            StatusChip(
                modifier = Modifier
                    .fillMaxWidth(),
                textModifier = Modifier
                    .fillMaxWidth(),
                text = if (schedule.note.isEmpty()) {
                    stringResource(R.string.not_done)
                } else {
                    stringResource(R.string.done)
                },
                textAlign = TextAlign.Center,
                color = color,
            )
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp16)
            )
            Text(
                text = schedule.title,
                color = DarkGray,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp4)
            )
            Text(
                text = "${schedule.date} • ${formatToTime(schedule.time)}",
                color = MediumGray,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp8)
            )
            Text(
                text = schedule.note.ifEmpty { "Note" },
                color = DarkGray,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = Dimens.dp16),
                color = Gray
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp36),
                shape = RoundedLarge,
                colors = ButtonDefaults
                    .buttonColors(
                        containerColor = Secondary,
                        contentColor = Primary
                    ),
                onClick = onClick
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .size(Dimens.dp24)
                            .padding(end = Dimens.dp8),
                        imageVector = Icons.Default.RemoveRedEye,
                        tint = Primary,
                        contentDescription = stringResource(R.string.icon)
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        text = stringResource(R.string.logbook)
                    )
                }
            }
        }
    }
}