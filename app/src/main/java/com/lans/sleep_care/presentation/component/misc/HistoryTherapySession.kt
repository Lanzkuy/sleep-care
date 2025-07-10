package com.lans.sleep_care.presentation.component.misc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.therapy.Therapy
import com.lans.sleep_care.domain.model.therapy.TherapySchedule
import com.lans.sleep_care.presentation.component.items.HistoryTherapySessionItem
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.MediumGray
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.White
import com.lans.sleep_care.utils.generateWeeklyRanges

@Composable
fun HistoryTherapySession(
    modifier: Modifier,
    therapy: Therapy,
    schedules: List<TherapySchedule>,
    onIdentifyValueClick: () -> Unit,
    onItemClick: (week: Int, dateRange: Pair<String, String>) -> Unit
) {
    val dateRange = generateWeeklyRanges(
        startDate = therapy.startDate,
        endDate = therapy.endDate
    )

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.session_therapy_history),
                color = MediumGray,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Button(
                modifier = Modifier
                    .height(Dimens.dp32),
                shape = RoundedLarge,
                colors = ButtonDefaults
                    .buttonColors(
                        containerColor = Primary,
                        contentColor = White
                    ),
                onClick = onIdentifyValueClick
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = stringResource(R.string.view_identify_value)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(Dimens.dp8)
        )
        LazyColumn {
            itemsIndexed(schedules) { index, schedule ->
                HistoryTherapySessionItem(
                    schedule = schedule,
                    onClick = { onItemClick((index + 1), dateRange[index]) }
                )
            }
        }
    }
}