package com.lans.sleep_care.presentation.component.items

import android.widget.Toast
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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.misc.StatusChip
import com.lans.sleep_care.presentation.theme.Danger
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.Secondary
import com.lans.sleep_care.presentation.theme.Success
import com.lans.sleep_care.utils.formatToTime

@Composable
fun ScheduleItem(
    date: String,
    time: String,
    topic: String,
    link: String,
    note: String,
    onLogbookClick: () -> Unit,
    onNoteClick: () -> Unit
) {
    val color = if (note.isEmpty()) {
        Danger
    } else {
        Success
    }

    val dateTime = if (date.isEmpty()) {
        "Belum ditentukan"
    } else {
        "$date • ${formatToTime(time)}"
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
                text = if (note.isEmpty()) {
                    stringResource(R.string.not_done)
                } else {
                    stringResource(R.string.done)
                },
                textAlign = TextAlign.Center,
                color = color,
            )
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp8)
            )
            ScheduleContent(icon = painterResource(R.drawable.ic_time), value = dateTime)
            ScheduleContent(icon = painterResource(R.drawable.ic_videocam), value = topic)
            ScheduleContent(
                icon = painterResource(R.drawable.ic_link),
                isCopyable = true,
                value = link
            )
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp8)
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
                onClick = onLogbookClick
            ) {
                Text(text = stringResource(R.string.logbook))
            }
            if (note.isNotEmpty()) {
                Spacer(
                    modifier = Modifier
                        .height(Dimens.dp8)
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
                    onClick = onNoteClick
                ) {
                    Text(text = stringResource(R.string.psychologist_note))
                }
            }
        }
    }
}

@Composable
fun ScheduleContent(
    icon: Painter,
    value: String,
    valueStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    isCopyable: Boolean = false
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val toastMessage = stringResource(id = R.string.text_copied)

    Row(
        modifier = Modifier
            .padding(vertical = Dimens.dp4),
        horizontalArrangement = Arrangement.spacedBy(Dimens.dp4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(Dimens.dp20),
            painter = icon,
            contentDescription = stringResource(R.string.icon)
        )
        Text(
            modifier = Modifier.weight(1f),
            text = value,
            style = valueStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (isCopyable && value != "Belum ditentukan") {
            Icon(
                modifier = Modifier
                    .size(Dimens.dp16)
                    .clickable {
                        clipboardManager.setText(AnnotatedString(value))
                        Toast
                            .makeText(context, toastMessage, Toast.LENGTH_SHORT)
                            .show()
                    },
                imageVector = Icons.Default.ContentCopy,
                contentDescription = stringResource(R.string.icon)
            )
        }
    }
}