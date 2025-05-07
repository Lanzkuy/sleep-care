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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.misc.StatusChip
import com.lans.sleep_care.presentation.theme.Danger
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.Success

@Composable
fun ScheduleItem(
    date: String,
    topic: String,
    link: String,
    note: String,
    onNoteClick: () -> Unit
) {
    val color = if (note.isEmpty()) {
        Danger
    } else {
        Success
    }

    OutlinedCard(
        shape = RoundedLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.dp8)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(Dimens.dp16)
            ) {
                ScheduleContent(icon = painterResource(R.drawable.ic_time), value = date)
                ScheduleContent(icon = painterResource(R.drawable.ic_videocam), value = topic)
                ScheduleContent(icon = painterResource(R.drawable.ic_link), value = link)
                Spacer(
                    modifier = Modifier
                        .height(Dimens.dp8)
                )
                if (note.isNotEmpty()) {
                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = onNoteClick
                    ) {
                        Text(
                            text = stringResource(R.string.doctor_note),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            StatusChip(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = Dimens.dp16,
                        end = Dimens.dp16
                    ),
                text = if (note.isEmpty()) {
                    stringResource(R.string.not_done)
                } else {
                    stringResource(R.string.done)
                },
                color = color,
            )
        }
    }
}

@Composable
fun ScheduleContent(
    icon: Painter,
    value: String,
    valueStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Row(
        modifier = Modifier
            .padding(vertical = Dimens.dp4),
        horizontalArrangement = Arrangement.spacedBy(Dimens.dp4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(Dimens.dp20),
            painter = icon,
            contentDescription = stringResource(R.string.icon)
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = value,
            style = valueStyle
        )
    }
}